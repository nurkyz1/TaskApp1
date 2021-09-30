package com.example.taskapp.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.example.taskapp.R;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProfileFragment extends Fragment {

    private static final int RESULT_GALLERY = 1;
    private Uri uri;
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        img = (ImageView) root.findViewById(R.id.img);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivityForResult(i, RESULT_GALLERY);

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();
            img.setImageURI(uri);
        }
    }
}