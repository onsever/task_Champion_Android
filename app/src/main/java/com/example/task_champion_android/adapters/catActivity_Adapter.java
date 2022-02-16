package com.example.task_champion_android.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.activities.catActivityData;
import com.example.task_champion_android.databinding.CategoriesLayoutBinding;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.CategoryWithItems;

import java.util.ArrayList;
import java.util.List;

public class catActivity_Adapter extends RecyclerView.Adapter<catActivity_Adapter.CatViewHolder> {
    Context context;

    CategoriesLayoutBinding binding;
    public static int selectedIndex = 0;
    private List<CategoryWithItems> category;
    private CategoryClickListener categoryClickListener;



    public catActivity_Adapter(Context context, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categoryClickListener = categoryClickListener;
    }
    public void setCategories(List<CategoryWithItems> category) {
        this.category = category;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = CategoriesLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CatViewHolder(binding);

    }



    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding.catName.setText(category.get(position).getCategory().getName());
       // binding.catIcon.setImageResource(category.get(position).getCatImage());
        binding.getRoot().setOnClickListener(v -> {
            notifyItemChanged(position);
            selectedIndex = position;
            System.out.println(selectedIndex);
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListener.onItemDelete(category.get(position).getCategory());
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListener.onItemUpdate(category.get(position).getCategory(),selectedIndex);
            }
        });




    }

    @Override
    public int getItemCount() {
        if (category == null || category.size() == 0) {
            return 0;
        }

        return category.size();
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;

        public CatViewHolder(@NonNull CategoriesLayoutBinding binding) {
            super(binding.getRoot());


        }
    }
    public interface  CategoryClickListener{
        void onItemUpdate(Category category, int selectedIndex);
        void onItemDelete(Category category);


    }

}
