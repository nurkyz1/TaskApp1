package com.example.taskapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentProfileBinding;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProfileFragment extends Fragment {

   private ActivityResultLauncher<String> mGetContent;
   private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
}