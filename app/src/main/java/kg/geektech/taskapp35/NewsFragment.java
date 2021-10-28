package kg.geektech.taskapp35;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import kg.geektech.taskapp35.ui.models.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class NewsFragment extends Fragment  {
    private EditText editText;
    private  News news;

    public NewsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseStorage storage =  FirebaseStorage. getInstance ();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v ->
                sendData());
        news = (News) requireArguments().getSerializable("news");
        if (news !=null){
            editText.setText(news.getTitle());
        }

    }
    public void includesForCreateReference() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images");
        StorageReference spaceRef = storageRef.child("images/space.jpg");
    }
        private void sendData() {
        String text = editText.getText().toString();
        if (news==null){
            news = new News(text);
            news.setCreateAd(System.currentTimeMillis());
            news.setEmail(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
            saveToFireStore(news);
        }else {
            news.setTitle(text);
            update(news);
        }

      //  Bundle bundle = new Bundle();
        //bundle.putSerializable("news", news);
        //getParentFragmentManager().setFragmentResult("rk_news", bundle);

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
                Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(requireActivity(), "Error"+ task.getException(), Toast.LENGTH_SHORT).show();
            }
            close();
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}