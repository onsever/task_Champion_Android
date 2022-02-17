package com.example.task_champion_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.CategoriesAdapter;
import com.example.task_champion_android.adapters.TasksAdapter;
import com.example.task_champion_android.databinding.ActivityMainBinding;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.CategoryWithItems;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.helper.SwipeHelper;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;





public class MainActivity extends AppCompatActivity implements CategoriesAdapter.CategoryClickListener, TasksAdapter.ItemClickListener {

    private ActivityMainBinding binding;
    private CategoriesAdapter categoriesAdapter;
    private TasksAdapter tasksAdapter;
    private AlertDialog.Builder addNewTaskDialog;
    private EditText taskTextField;
    private CategoryViewModel categoryViewModel;
    private long categoryId;
    private Category category;
//    private int selectedIndex = 0;

    public static final String ITEM_ID = "itemId";
    public static final String CAT_ID = "CategoryId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hideStatusBar();
        configureAdapters();
        configureButtonListeners();
        configureSearchBarListeners();
        initViewModel();

    }

    private void initViewModel() {
        categoryViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(CategoryViewModel.class);

        categoryViewModel.getCategoryWithItems().observe(this, categoryWithItems -> {
            CategoriesAdapter categoriesAdapter1 = new CategoriesAdapter(this,this);
            binding.categoriesRecyclerView.setAdapter(categoriesAdapter1);
            categoriesAdapter1.setCategories(categoryWithItems);
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

        addNewTaskDialog.setPositiveButton("Add", (dialog, i) -> {
            String taskName = taskTextField.getText().toString();

            if (TextUtils.isEmpty(taskName)) {
                return;
            }

            Item item = new Item(taskName, categoryId, "", "");
            categoryViewModel.insertItemToCategory(category, item);

            dialog.dismiss();
        });

        addNewTaskDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        addNewTaskDialog.show();
    }

    private void configureButtonListeners() {
        binding.sortByTaskButton.setOnClickListener(v -> {
            categoryViewModel.getItemsSortedByName(categoryId).observe(this, _list -> {
                TasksAdapter adapter = new TasksAdapter(MainActivity.this, MainActivity.this);
                adapter.setItems(_list);
                binding.tasksRecyclerView.setAdapter(adapter);
            });
        });

        binding.sortByDateButton.setOnClickListener(v -> {
            categoryViewModel.getItemsSortedByDate(categoryId).observe(this, _list -> {
                TasksAdapter adapter = new TasksAdapter(MainActivity.this, MainActivity.this);
                adapter.setItems(_list);
                binding.tasksRecyclerView.setAdapter(adapter);
            });
        });

        binding.addTaskButton.setOnClickListener(v -> createAddTaskAlert());

        binding.moveToCategories.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
            startActivity(intent);
        });
    }

    private void configureSearchBarListeners() {

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                categoryViewModel.searchItemByName(categoryId, query).observe(MainActivity.this, _itemList ->{
                    TasksAdapter newTaskAdapter = new TasksAdapter(MainActivity.this, MainActivity.this);
                    binding.tasksRecyclerView.setAdapter(newTaskAdapter);
                    newTaskAdapter.setItems(_itemList);
                    if (_itemList.isEmpty()){
                        Toast.makeText(getApplicationContext(),
                                "No records found",Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                categoryViewModel.searchItemByCatID(categoryId).observe(MainActivity.this, _itemList -> {
                    TasksAdapter newTaskAdapter = new TasksAdapter(MainActivity.this, MainActivity.this);
                    binding.tasksRecyclerView.setAdapter(newTaskAdapter);
                    newTaskAdapter.setItems(_itemList);
                });
                return false;
            }
        });
    }

    @Override
    public void onItemClick(Category category, int selectedIndex, CategoryWithItems categoryWithItems) {
        System.out.println("Selected Index: " + selectedIndex);
        //isCompletedCounter = 0;
        this.category = category;
//        this.selectedIndex = selectedIndex;

        tasksAdapter = new TasksAdapter(MainActivity.this, this);
        binding.tasksRecyclerView.setAdapter(tasksAdapter);
        tasksAdapter.setItems(categoryWithItems.getItemList());

//        this.itemList = categoryWithItems.getItemList();

//        for (int i = 0; i < itemList.size(); i++) {
//            if (itemList.get(i).isCompleted()) {
//                isCompletedCounter++;
//            }
//        }
//
//        categoriesAdapter.setProgress(isCompletedCounter);
    }

    @Override
    public void getCategoriesId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void onItemClickedOn(Item item) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(ITEM_ID, item.getId());
        intent.putExtra(CAT_ID, this.categoryId);
        startActivity(intent);
    }

    @Override
    public void onItemDelete(Item item) {
        categoryViewModel.deleteItem(item);
    }

    @Override
    public void onItemUpdate(Item item) {
        item.setCompleted(!item.isCompleted());
        categoryViewModel.updateItem(category, item);
    }

}
