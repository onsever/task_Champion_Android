package com.example.task_champion_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.databinding.CategoriesLayoutBinding;
import com.example.task_champion_android.databinding.CategoriesRowBinding;

import java.util.ArrayList;

public class catActivity_Adapter extends RecyclerView.Adapter<catActivity_Adapter.CatViewHolder> {
    Context context;
    ArrayList<catActivityData> categories;
    CategoriesLayoutBinding binding;
    public static int selectedIndex = 0;


    public catActivity_Adapter(Context context, ArrayList<catActivityData> categories) {
        this.context = context;
        this.categories = categories;
    }



    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = CategoriesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CatViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull catActivity_Adapter.CatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding.catName.setText(categories.get(position).getCat_name());
        binding.catIcon.setImageResource(categories.get(position).getCatImage());
        binding.getRoot().setOnClickListener(v -> {
            notifyItemChanged(position);
            selectedIndex = position;
            System.out.println(selectedIndex);
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    class CatViewHolder extends RecyclerView.ViewHolder {

        public CatViewHolder(@NonNull CategoriesLayoutBinding binding) {
            super(binding.getRoot());
        }
    }
}
