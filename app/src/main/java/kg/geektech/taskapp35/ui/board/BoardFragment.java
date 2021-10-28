package kg.geektech.taskapp35.ui.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import kg.geektech.taskapp35.Prefs;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.FragmentBoardBinding;

public class BoardFragment extends Fragment implements BoardAdapter.Finish {
    FragmentBoardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        BoardAdapter adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        adapter.setFinish(this);
        binding.indicator.setViewPager2(binding.viewPager);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
       binding.btnSkip.setOnClickListener(v -> {
           NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
           navController.navigateUp();
       });

    }
    private  void  close(){
        NavController navController= Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

    @Override
    public void btnFinish() {
        new Prefs(requireContext()).saveBoardState();
        close();
    }
}