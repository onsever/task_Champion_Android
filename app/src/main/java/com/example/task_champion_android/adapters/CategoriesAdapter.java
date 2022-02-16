package com.example.task_champion_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.databinding.CategoriesRowBinding;
import com.example.task_champion_android.db.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CatViewHolder> {

    Context context;
    CategoriesRowBinding binding;
    public static int selectedIndex = 0;
    private List<Category> categories;
    private CategoryClickListener categoryClickListener;

    private int numberOfItems;

    public CategoriesAdapter(Context context, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryClickListener = categoryClickListener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
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
        categoryClickListener.getCategoriesId(categories.get(0).getId());
        binding.categoryName.setText(categories.get(position).getName());
        binding.taskCounter.setText(String.format(context.getResources().getString(R.string.taskCounter), numberOfItems));
//        binding.itemCounter.setProgress(categories.get(position).getItemCounter());
        //binding.itemCounter.setMax(categories.get(position).getItems().size());
        binding.cardView.setOnClickListener(v -> {
            selectedIndex = holder.getAdapterPosition();
            categoryClickListener.onItemClick(categories.get(selectedIndex), selectedIndex);
            categoryClickListener.getCategoriesId(categories.get(selectedIndex).getId());
        });

    }

    static class CatViewHolder extends RecyclerView.ViewHolder {

        public CatViewHolder(@NonNull CategoriesRowBinding binding) {
            super(binding.getRoot());
        }
    }

    public interface CategoryClickListener {
        void onItemClick(Category category, int selectedIndex);
        void getCategoriesId(long categoryId);
    }

}
