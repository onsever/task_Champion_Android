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
import com.example.task_champion_android.viewmodel.ItemViewModel;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoriesAdapter.CategoryClickListener, TasksAdapter.ItemClickListener {

    private ActivityMainBinding binding;
    private CategoriesAdapter categoriesAdapter;
    private TasksAdapter tasksAdapter;

    private AlertDialog.Builder addNewTaskDialog;
    private EditText taskTextField;

    private CategoryViewModel categoryViewModel;
    private ItemViewModel itemViewModel;
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
        categoryViewModel.getAllCategoryList();
        itemViewModel.getAllItemList(categoryId);

    }

    private void initViewModel() {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories == null) {
                } else {
                    categoriesAdapter.setCategories(categories);
                }
            }
        });

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getItemsList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items == null) {
                } else {
                    tasksAdapter.setItems(items);
                }
            }
        });

    }


    private void configureAdapters() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        categoriesAdapter = new CategoriesAdapter(this, this);
        tasksAdapter = new TasksAdapter(this, this);
        binding.catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,CategoriesActivity.class);
                startActivity(intent);

            }
        });
        binding.tasksRecyclerView.setAdapter(tasksAdapter);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);
        binding.categoriesRecyclerView.setLayoutManager(linearLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.tasksRecyclerView);

    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    itemViewModel.deleteItem(itemViewModel.getItemsList().getValue().get(position));
                    itemViewModel.getAllItemList(categoryId);
                    break;

                case ItemTouchHelper.RIGHT:
//                    boolean isCompleted = !(categories.get(CategoriesAdapter.selectedIndex).getItems().get(position).isCompleted());
//                    categories.get(CategoriesAdapter.selectedIndex).getItems().get(position).setCompleted(isCompleted);
//                    tasksAdapter.notifyItemChanged(position);
                    break;
            }
        }
    };

    private void createAddTaskAlert() {
        taskTextField = new EditText(this);
        addNewTaskDialog = new AlertDialog.Builder(this);
        addNewTaskDialog.setTitle("Add a new Task");
        addNewTaskDialog.setMessage("Please enter the name of the task.");
        addNewTaskDialog.setView(taskTextField);

        addNewTaskDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskName = taskTextField.getText().toString();

                if (TextUtils.isEmpty(taskName)) {
                    return;
                }

                itemViewModel.insertItem(new Item(taskName, categoryId, false));

                dialog.dismiss();
            }
        });

        addNewTaskDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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
        System.out.println("Selected category name: " + category.getCategoryName());
    }

    @Override
    public void getCategoriesId(int categoryId) {
        this.categoryId = categoryId;
        itemViewModel.getAllItemList(categoryId);
        tasksAdapter.notifyDataSetChanged();
        System.out.println("Category ID" + this.categoryId);
    }

    @Override
    public void onItemClickedOn(Item item) {

    }

}
