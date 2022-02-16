package com.example.task_champion_android.activities;



import static com.example.task_champion_android.db.AppDatabase.INSTANCE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.TasksAdapter;
import com.example.task_champion_android.adapters.catActivity_Adapter;
//import com.example.task_champion_android.catActivity_Adapter;
import com.example.task_champion_android.databinding.ActivityCategoriesBinding;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.db.QueryDao;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements catActivity_Adapter.CategoryClickListener{
    //private final ArrayList<catActivityData> categories = new ArrayList<>();
    private ActivityCategoriesBinding binding;
    private AlertDialog.Builder addNewTaskDialog;
    private EditText taskTextField;
    private CategoryViewModel categoryViewModel;
    private catActivity_Adapter catActivity_adapter;
    private long categoryId;
    private Category category;
    private int selectedIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         catActivity_adapter= new catActivity_Adapter(this,this);
        binding.categ.setAdapter(catActivity_adapter);
        binding.categ.setLayoutManager(new LinearLayoutManager(this));

        binding.btnAddNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTaskAlert();
            }
        });
        initViewModel();

    }
    private void initViewModel() {
        categoryViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getCategoryWithItems().observe(this, categoryWithItems -> {
            catActivity_adapter.setCategories(categoryWithItems);
        });


    }


    private void createNewTaskAlert() {
        taskTextField = new EditText(this);
        addNewTaskDialog = new AlertDialog.Builder(this);
        addNewTaskDialog.setTitle("Add a new Category");
        addNewTaskDialog.setMessage("Please enter the name of the category.");
        addNewTaskDialog.setView(taskTextField);

        addNewTaskDialog.setPositiveButton("Add", (dialog,i) -> {
            String catName = taskTextField.getText().toString();

            if (TextUtils.isEmpty(catName)) {
                return;
            }

                Category category = new Category(catName);
                 categoryViewModel.insertCategory(category);




            dialog.dismiss();
        });

        addNewTaskDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        addNewTaskDialog.show();
    }

    private void updateTaskAlert(Category category) {
        taskTextField = new EditText(this);
        addNewTaskDialog = new AlertDialog.Builder(this);
        addNewTaskDialog.setTitle("Update the Category");
        addNewTaskDialog.setMessage("Please enter the name of the task.");
        addNewTaskDialog.setView(taskTextField);

        addNewTaskDialog.setPositiveButton("Update", (dialog, i) -> {
            String catName = taskTextField.getText().toString();

            if (TextUtils.isEmpty(catName)) {
                return;
            }


            Category category1 = new Category(catName);
            categoryViewModel.updateCategory(category1);

            dialog.dismiss();
        });

        addNewTaskDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        addNewTaskDialog.show();
    }


    @Override
    public void onItemUpdate(Category category, int selectedIndex) {
            categoryViewModel.updateCategory(category);
        updateTaskAlert(category);
    }

    @Override
    public void onItemDelete(Category category) {
        categoryViewModel.deleteCategory(category);
    }
}