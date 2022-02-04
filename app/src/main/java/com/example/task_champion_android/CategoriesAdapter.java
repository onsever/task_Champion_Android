package com.example.task_champion_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.databinding.CategoriesRowBinding;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CatViewHolder> {

    Context context;
    ArrayList<Category> categories;
    CategoriesRowBinding binding;
    public static int selectedIndex = 0;

    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = CategoriesRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CatViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        binding.categoryName.setText(categories.get(position).getName());
        binding.taskCounter.setText(String.format(context.getResources().getString(R.string.taskCounter), categories.get(position).getTaskCounter()));
        binding.itemCounter.setProgress(categories.get(position).getItemCounter());
        binding.getRoot().setOnClickListener(v -> {
            notifyItemChanged(position);
            selectedIndex = position;
            System.out.println(selectedIndex);
        });
//        holder.categoryName.setText(categories.get(position).getName());
//        holder.taskCounter.setText(String.format(context.getResources().getString(R.string.taskCounter), categories.get(position).getTaskCounter()));
//        holder.itemCounter.setProgress(categories.get(position).getItemCounter());
//        holder.itemView.setOnClickListener(v -> {
//            notifyItemChanged(position);
//            selectedIndex = position;
//        });
    }

    class CatViewHolder extends RecyclerView.ViewHolder {

        public CatViewHolder(@NonNull CategoriesRowBinding binding) {
            super(binding.getRoot());
        }
    }
}
