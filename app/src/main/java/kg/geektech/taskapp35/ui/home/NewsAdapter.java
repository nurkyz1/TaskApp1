package kg.geektech.taskapp35.ui.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import kg.geektech.taskapp35.OnItemClickListener;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.ListNewsBinding;
import kg.geektech.taskapp35.ui.models.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final ArrayList<News> list = new ArrayList<>();
    ConstraintLayout listNews;
    private OnItemClickListener onItemClickListener;

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
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(0);
    }

    public void addItems(List<News> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListeners(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public News getItem(int pos) {
        return list.get(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;

        private final kg.geektech.taskapp35.databinding.ListNewsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ListNewsBinding.bind(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
            if (getAdapterPosition() % 2 == 0) {
                listNews.setBackgroundColor(Color.GRAY);
            } else
                listNews.setBackgroundColor(Color.WHITE);
        }

        public void bind(News news) {
            binding.textTitle.setText(news.getTitle());
            binding.email.setText(news.getEmail());
            binding.textTime.setText(changeTypeOfDateToAgo(news.getCreatedAt()));
            binding.textVie.setText(String.valueOf(news.getView_count()));
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
            Glide.with(itemView.getContext()).load(news.getImageUrl())
                    .override(500, 500).circleCrop().into(binding.img);

        }

        private String changeTypeOfDateToAgo(Object createdAt) {

            long milliSecPerMinute = 60 * 1000;
            long milliSecPerHour = milliSecPerMinute * 60;
            long milliSecPerDay = milliSecPerHour * 24;
            long milliSecPerMonth = milliSecPerDay * 30;
            long milliSecPerYear = milliSecPerDay * 365;
            String agoTime;
            if (createdAt != null) {
                long msExpired = System.currentTimeMillis() - (Long) createdAt;
                if (msExpired < milliSecPerMinute) {
                    if (Math.round(msExpired / 1000) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / 1000)) + " second ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / 1000) + " seconds ago...");
                    }
                } else if (msExpired < milliSecPerHour) {
                    if (Math.round(msExpired / milliSecPerMinute) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago... ";
                    }
                } else if (msExpired < milliSecPerDay) {
                    if (Math.round(msExpired / milliSecPerHour) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago... ";
                    }
                } else if (msExpired < milliSecPerMonth) {
                    if (Math.round(msExpired / milliSecPerDay) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago... ";
                    }
                } else if (msExpired < milliSecPerYear) {
                    if (Math.round(msExpired / milliSecPerMonth) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago... ";
                    }
                } else {
                    if (Math.round(msExpired / milliSecPerYear) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago...";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago...";
                    }
                }
            }
            return agoTime = "time not found";
        }
    }
}

