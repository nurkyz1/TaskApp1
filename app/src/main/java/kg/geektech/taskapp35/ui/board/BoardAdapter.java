package kg.geektech.taskapp35.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.PagerBoardBinding;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private final String [] titles = new String[]{"News ","Add news", "World news"};
    private  final  String [] description = new String[]{" Reading new news","You can add news yourself", "Reading news world" };
    private  final  int [] images= new int[]{R.raw.one_image,R.raw.two_image,R.raw.three_image};
    private PagerBoardBinding binding;
    private Finish finish;

    public void setFinish(Finish finish) {
        this.finish = finish;
    }

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
           binding.textDesc.setText(description[position]);
            binding.btnNext.setOnClickListener(v -> finish.btnFinish());
        }
    }
    interface Finish{
        void btnFinish();
    }
}
