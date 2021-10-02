package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.databinding.ListNewsBinding;
import com.example.taskapp.ui.models.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<News> list = new ArrayList<>();
    LinearLayout listNews;

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        listNews = view.findViewById(R.id.listNews);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (position % 2 == 0) {
            listNews.setBackgroundColor(Color.WHITE);
        } else
            listNews.setBackgroundColor(Color.DKGRAY);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;

        private final com.example.taskapp.databinding.ListNewsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListNewsBinding.bind(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
        }

        public void bind(News news) {
            textTitle.setText(news.getTitle());
            binding.textTime.setText(DateUtils.formatDateTime(itemView.getContext(),news.getTime(),DateUtils.FORMAT_SHOW_TIME));
            binding.getRoot().setOnLongClickListener((View v) -> {
                AlertDialog.Builder dialog = new AlertDialog.Builder(binding.getRoot().getContext());
                String positive = "Да";
                String negative = "Нет";
                dialog.setMessage("Вы хотите удолить ?");
                dialog.setPositiveButton(positive, (dialog1, which) -> {
                    list.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                });
                dialog.setNegativeButton(negative, null);
                dialog.show();
                return true;
            });

        }
    }


}

