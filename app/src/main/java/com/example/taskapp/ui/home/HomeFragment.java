package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.ui.models.News;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
   private NewsAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout listNews;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listNews = view.findViewById(R.id.listNews);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> openFragment());

        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), (requestKey, result) -> {
            News news= (News) result.getSerializable("news");
           Log.e("Home","text = "+ news.getTitle());
            adapter.addItem(news);
        });
    initList();
    }
    private  void  initList(){
        recyclerView.setAdapter(adapter);

    }
    private  void  openFragment(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    navController.navigate(R.id.blankFragment);
    }

    }

