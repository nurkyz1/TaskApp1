package com.example.taskapp.ui.board;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.databinding.PagerBoardBinding;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private final String [] titles = new String[]{"","", ""};
    private  final  String [] description = new String[]{};
    private  final  int [] images= new int[]{R.raw.one_image,R.raw.two_image,R.raw.three_image};
    private PagerBoardBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PagerBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return  new ViewHolder(binding);
   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(position);

        if (position == 2) {
            binding.btnNext.setVisibility(View.VISIBLE);
        }else{
            binding.btnNext.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(PagerBoardBinding binding) {
            super(binding.getRoot());
        }
        public  void  bind(int position){
           binding.textTitle.setText(titles[position]);
           binding.gif.setAnimation(images[position]);
           binding.btnNext.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   NavController navController = Navigation.findNavController((Activity) itemView.getContext(),R.id.nav_host_fragment);
                   navController.navigateUp();
               }
           });
        }
    }
}
