package com.example.task_champion_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.task_champion_android.adapters.CategoriesAdapter;
import com.example.task_champion_android.adapters.TasksAdapter;
import com.example.task_champion_android.databinding.ActivityMainBinding;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoriesAdapter.CategoryClickListener, TasksAdapter.ItemClickListener {

    private ActivityMainBinding binding;
    private CategoriesAdapter categoriesAdapter;
    private TasksAdapter tasksAdapter;

    private AlertDialog.Builder addNewTaskDialog;
    private EditText taskTextField;

    private CategoryViewModel categoryViewModel;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hideStatusBar();
        configureAdapters();
        configureButtonListeners();

        initViewModel();

    }

    private void initViewModel() {
        categoryViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getCategories().observe(this, categories -> {
            categoriesAdapter.setCategories(categories);
        });

    }


    private void configureAdapters() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        categoriesAdapter = new CategoriesAdapter(this, this);
        tasksAdapter = new TasksAdapter(this, this);

        binding.tasksRecyclerView.setAdapter(tasksAdapter);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);
        binding.categoriesRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void createAddTaskAlert() {
        taskTextField = new EditText(this);
        addNewTaskDialog = new AlertDialog.Builder(this);
        addNewTaskDialog.setTitle("Add a new Task");
        addNewTaskDialog.setMessage("Please enter the name of the task.");
        addNewTaskDialog.setView(taskTextField);

        addNewTaskDialog.setPositiveButton("Add", (dialog, which) -> {
            String taskName = taskTextField.getText().toString();

            if (TextUtils.isEmpty(taskName)) {
                return;
            }

            Category category = new Category(taskName);
            categoryViewModel.insertCategory(category);

            dialog.dismiss();
        });

        addNewTaskDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        addNewTaskDialog.show();
    }

    private void configureButtonListeners() {
        binding.sortByTaskButton.setOnClickListener(v -> {

        });

        binding.sortByDateButton.setOnClickListener(v -> {

        });

        binding.addTaskButton.setOnClickListener(v -> createAddTaskAlert());
    }

    @Override
    public void onItemClick(Category category, int selectedIndex) {
        System.out.println("Selected Index: " + selectedIndex);
    }

    @Override
    public void getCategoriesId(long categoryId) {

    }

    @Override
    public void onItemClickedOn(Item item) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

}
