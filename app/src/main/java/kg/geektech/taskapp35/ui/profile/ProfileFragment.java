package kg.geektech.taskapp35.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kg.geektech.taskapp35.Prefs;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ActivityResultLauncher<String> mGetContent;
    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveName();
        saveImage();
        save();
        openGallery();
        googleSignOut();
   //     emailName();

    }
    private  void  save(){
        mGetContent  = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
            Glide.with(requireActivity()).load(result).circleCrop().into(binding.img);
            Prefs prefs= new Prefs(requireContext());
            prefs.setForImage(result);});
        }
    private void openGallery() {
        binding.img.setOnClickListener(v -> mGetContent.launch("image/*"));
    }
    public void saveName() {
       binding.editName.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
            Prefs prefs = new Prefs(requireContext());
            prefs.setForName(s.toString());
           }
       });
    }
    public  void  saveImage() {
        Prefs prefs = new Prefs(requireContext());
        String uri = prefs.getForImage();
        String name = prefs.getForName();
        Glide.with(requireActivity()).load(uri).circleCrop().into(binding.img);
        binding.editName.setText(name);
    }
    private  void  emailName(){
        if (FirebaseAuth.getInstance().getCurrentUser()!= null ) {
            binding.editName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString());
        }else {

        }
    }
    private void  googleSignOut() {
        binding.signOut.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(binding.signOut.getContext());
            String positive = "ДА";
            String negative ="НЕТ";
            alert.setMessage("Вы хотите выйти с аккаунта?");
            alert.setPositiveButton(positive, (dialog, which) ->{
                    FirebaseAuth.getInstance().signOut();
                Toast.makeText(requireActivity(), "SignOut: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.loginFragment);

            });
            alert.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alert.show();
        });

    }}
