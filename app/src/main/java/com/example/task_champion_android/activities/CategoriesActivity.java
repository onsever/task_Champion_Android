package com.example.task_champion_android.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.catActivity_Adapter;
import com.example.task_champion_android.catActivityData;
//import com.example.task_champion_android.catActivity_Adapter;
import com.example.task_champion_android.databinding.ActivityCategoriesBinding;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    private final ArrayList<catActivityData> categories = new ArrayList<>();
    private ActivityCategoriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dummyTesting();
    }
    private void dummyTesting() {
        categories.add(new catActivityData("Business", R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("School",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Home",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Friends",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Family",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Hangout",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Work",R.drawable.ic_baseline_category_24));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        catActivity_Adapter adapter = new catActivity_Adapter(this, categories);
        binding.categ.setAdapter(adapter);
        binding.categ.setLayoutManager(linearLayoutManager);

        binding.btnAddNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CategoriesActivity.this);
                dialog.setContentView(R.layout.add_category);
                EditText catET=dialog.findViewById(R.id.editTextCategory);
                Button dialogBtnAction = dialog.findViewById(R.id.btnAddCat);
                dialogBtnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String catName="";
                        if(!catET.getText().toString().equals("")) {
                            catName = catET.getText().toString();
                        }
                        else
                        {

                            Toast.makeText(CategoriesActivity.this, "Enter Category Name", Toast.LENGTH_SHORT).show();
                        }
                        categories.add(new catActivityData(catName,R.drawable.ic_baseline_category_24));
                        adapter.notifyItemInserted(categories.size());
                        binding.categ.scrollToPosition(categories.size()-1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }


}