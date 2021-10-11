package com.example.taskapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private final String [] titles = new String[]{"Fast","Free", "Powerful"};
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
       private final TextView textTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle =itemView.findViewById(R.id.textTitle);
        }
        public  void  bind(int position){
            textTitle.setText(titles[position]);
        }
    }
}
