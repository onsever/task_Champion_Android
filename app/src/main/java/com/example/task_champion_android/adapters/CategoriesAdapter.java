package com.example.task_champion_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.databinding.CategoriesRowBinding;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.CategoryWithItems;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CatViewHolder> {

    Context context;
    CategoriesRowBinding binding;
    public static int selectedIndex = 0;
    private List<CategoryWithItems> categories;
    private CategoryClickListener categoryClickListener;

    private int numberOfItems;

    public CategoriesAdapter(Context context, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryClickListener = categoryClickListener;
    }

    public void setCategories(List<CategoryWithItems> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setProgress(int value) {
        this.numberOfItems = value;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CategoriesRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CatViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        if (categories == null || categories.size() == 0) {
            return 0;
        }

        return categories.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        categoryClickListener.getCategoriesId(categories.get(0).getCategory().getId());
        categoryClickListener.onItemClick(categories.get(0).getCategory(), holder.getAdapterPosition(), categories.get(0));
        binding.categoryName.setText(categories.get(position).getCategory().getName());
        binding.taskCounter.setText(String.format(context.getResources().getString(R.string.taskCounter), categories.get(position).getItemListSize()));
        binding.cardView.setOnClickListener(v -> {
            selectedIndex = holder.getAdapterPosition();
            categoryClickListener.onItemClick(categories.get(selectedIndex).getCategory(), selectedIndex, categories.get(position));
            categoryClickListener.getCategoriesId(categories.get(selectedIndex).getCategory().getId());
        });
        binding.itemCounter.setMax(categories.get(position).getItemListSize());
        binding.itemCounter.setProgress(numberOfItems);
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {

        public CatViewHolder(@NonNull CategoriesRowBinding binding) {
            super(binding.getRoot());
        }
    }

    public interface CategoryClickListener {
        void onItemClick(Category category, int selectedIndex, CategoryWithItems categoryWithItems);
        void getCategoriesId(long categoryId);
    }

}
