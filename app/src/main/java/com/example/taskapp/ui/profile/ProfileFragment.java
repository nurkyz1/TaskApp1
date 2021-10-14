package com.example.taskapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProfileFragment extends Fragment {

    private static final int RESULT_GALLERY = 1;
    private Uri uri;
    ImageView img;
    EditText editName;
    ActivityResultLauncher resultLauncher;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        img = (ImageView) root.findViewById(R.id.img);
        editName = root.findViewById(R.id.editName);
        saveName();
        saveImage();
        save();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img.setOnClickListener(v -> {
            openGallery();
        });
        mGetContent= registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                    img.setImageURI(uri);
                }

            });
        }
       // img.setOnClickListener(v -> {
         //   Intent i = new Intent();
           // i.setAction(Intent.ACTION_PICK);
            //i.setType("image/*");
            //startActivityForResult(i, RESULT_GALLERY);

        //});


  //  @Override
    //public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == RESULT_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
          //  uri = data.getData();
            //img.setImageURI(uri);
        //}

    private void openGallery() {
        mGetContent.launch("image/*");
    }


    public  void saveName(){
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs prefs = new Prefs(requireContext());
                prefs.setForName(editName.getText().toString().trim());
            }
        });
        Prefs prefs = new Prefs(requireContext());
        editName.setText(prefs.getForName());
    }
    public  void  saveImage(){
        Prefs prefs = new Prefs(requireContext());
        String uri= prefs.getForImage();
        String name = prefs.getForName();
        Glide.with(requireActivity()).load(uri).circleCrop().into(img);
        editName.setText(name);
    }
    private  void  save(){
        resultLauncher  = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
            Glide.with(requireActivity()).load(result).circleCrop().into(img);
            Prefs prefs= new Prefs(requireContext());
            prefs.setForImage(result);});
    }


}