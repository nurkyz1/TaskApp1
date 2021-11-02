package kg.geektech.taskapp35.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.FragmentNewsNewBinding;
import kg.geektech.taskapp35.ui.home.NewsAdapter;
import kg.geektech.taskapp35.ui.models.News;


public class NewsNewFragment extends Fragment {
    private FragmentNewsNewBinding binding;
    private NewsAdapter adapter;
    private News news;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsNewBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news = (News) requireArguments().getSerializable("key");
        if (news !=null){
        binding.textviewEmail.setText(news.getEmail());
        binding.textviewTitle.setText(news.getTitle());
        Glide.with(requireContext()).load(news.getImageUrl()).into(binding.imageView);
        }
    }
}