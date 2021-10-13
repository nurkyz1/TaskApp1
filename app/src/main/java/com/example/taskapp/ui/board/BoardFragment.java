package com.example.taskapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentBoardBinding;
import com.example.taskapp.databinding.PagerBoardBinding;

public class BoardFragment extends Fragment {
    FragmentBoardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
      View view = binding.getRoot();
    return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        BoardAdapter adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        binding.indicator.setViewPager2(binding.viewPager);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
       binding.btnSkip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               close();
           }
       });

    }
    private  void  close(){
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        NavController navController= Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}