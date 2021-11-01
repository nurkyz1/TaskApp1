package kg.geektech.taskapp35;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;
import java.util.UUID;
import kg.geektech.taskapp35.ui.models.News;

public class NewsFragment extends Fragment implements OnItemClickListener  {
    private EditText editText;
    private  News news;
    private Uri uri;
    private ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        img = view.findViewById(R.id.imageV);
        img.setOnClickListener(v -> openGallery());
        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v ->
                sendData());
        news = (News) requireArguments().getSerializable("news");
        if (news !=null){
            editText.setText(news.getTitle());
        }

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImage.launch(intent);

    }
    ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        assert result.getData() != null;
                        uri = result.getData().getData();
                    img.setImageURI(uri);
                    }
                }
            });


    private void sendData() {
        String text = editText.getText().toString().trim();
        if (news==null){
            news = new News(text);
            news.setCreatedAt(System.currentTimeMillis());
            news.setView_count(0);
            news.setEmail(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
            if (uri !=null)uploudFile();
            else saveToFireStore(null);
        }else {
            news.setTitle(text);
            update(news);
        }
      //  Bundle bundle = new Bundle();
        //bundle.putSerializable("news", news);
        //getParentFragmentManager().setFragmentResult("rk_news", bundle);

    }

    private void uploudFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String uuid= UUID.randomUUID().toString();
      StorageReference reference=  storage.getReference().child("images/"+uuid+".jpg");
        UploadTask task = reference.putFile(uri);
        Task<Uri> uriTask = task.continueWithTask(task1 -> reference.getDownloadUrl()).addOnCompleteListener(task11 -> {
            if (task11.isSuccessful()) {
                news.setImageUrl(task11.getResult().toString());
                saveToFireStore(news);
            } else {
                Toast.makeText(requireActivity(), "Error: " + task11.getException()
                        , Toast.LENGTH_SHORT).show();
            }

        });
    }

    private  void update(News news){
        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("news").document(news.getId()).update("title",news.getTitle()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(requireActivity(), "Error"+ task.getException(), Toast.LENGTH_SHORT).show();
            }
            close();
        });
    }
    private  void  saveToFireStore(News news){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news").add(news).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                news.setId(task.getResult().getId());
                saveToRoom(news);
                Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(requireActivity(), "Error"+ task.getException(), Toast.LENGTH_SHORT).show();
            }
            close();
        });
    }

    private void saveToRoom(News news) {
        App.getInstance().getDatabase().newsDao().insert(news);
    }


    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

    @Override
    public void onItemClick(int pos) {
        openGallery();
    }
}