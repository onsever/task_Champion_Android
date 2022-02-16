package com.example.task_champion_android.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class catActivity_Adapter extends RecyclerView.Adapter<CategoryViewModel,catActivity_Adapter.CatViewHolder> {
    Context context;
    ArrayList<catActivityData> categories;
    CategoriesLayoutBinding binding;
    public static int selectedIndex = 0;
    private OnItemClickListener listener;


    public catActivity_Adapter(Context context, ArrayList<catActivityData> categories) {
        this.context = context;
        this.categories = categories;
    }
    public void setCategories(List<Category> category) {
        this.categories = categories;
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
    public void onBindViewHolder(@NonNull CategoryViewModel holder, int position) {

    }


    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding.catName.setText(categories.get(position).getCat_name());
        binding.catIcon.setImageResource(categories.get(position).getCatImage());
        binding.getRoot().setOnClickListener(v -> {
            notifyItemChanged(position);
            selectedIndex = position;
            System.out.println(selectedIndex);
        });
            binding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog=new Dialog(context);
                    dialog.setContentView(R.layout.add_category);
                    TextView txtTitle=dialog.findViewById(R.id.textView);
                    EditText catET=dialog.findViewById(R.id.editTextCategory);
                    Button dialogBtnAction = dialog.findViewById(R.id.btnAddCat);
                    txtTitle.setText("Update Category");
                    dialogBtnAction.setText("UPDATE");
                    catET.setText(categories.get(position).getCat_name());
                    dialogBtnAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String catName="";
                            if(!catET.getText().toString().equals("")) {
                                catName = catET.getText().toString();
                            }
                            else
                            {
                                Toast.makeText(context, "Enter Category Name", Toast.LENGTH_SHORT).show();
                            }
                            categories.set(position,new catActivityData(catName, R.drawable.ic_baseline_category_24));
                            notifyItemChanged(position);
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            });



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;

        public CatViewHolder(@NonNull CategoriesLayoutBinding binding) {
            super(binding.getRoot());


        }
    }
    public interface OnItemClickListener {
        void onItemClick(Category category, int selectedIndex);
       // void getCategoriesId(long categoryId);
    }

}
