package com.example.task_champion_android;

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

import com.example.task_champion_android.databinding.CategoriesLayoutBinding;

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
    public void onBindViewHolder(@NonNull CatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding.catName.setText(categories.get(position).getCat_name());
        binding.catIcon.setImageResource(categories.get(position).getCatImage());
        binding.getRoot().setOnClickListener(v -> {
            notifyItemChanged(position);
            selectedIndex = position;
            System.out.println(selectedIndex);
        });

            binding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                    {
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
                                categories.set(position,new catActivityData(catName,R.drawable.ic_baseline_category_24));
                                notifyItemChanged(position);
                                dialog.dismiss();

                            }
                        });
                        dialog.show();


                }
            });

            binding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
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
}
