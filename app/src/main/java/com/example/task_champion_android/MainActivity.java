package com.example.task_champion_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_champion_android.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Category> categories = new ArrayList<>();
    private ActivityMainBinding binding;
    private CategoriesAdapter categoriesAdapter;
    private TasksAdapter tasksAdapter;

    private AlertDialog.Builder addNewTaskDialog;
    private EditText taskTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dummyTesting();
        configureAdapters();
        configureButtonListeners();
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
                    categories.get(CategoriesAdapter.selectedIndex).getItems().remove(position);
                    tasksAdapter.notifyItemRemoved(position);
                    break;

                case ItemTouchHelper.RIGHT:
                    boolean isCompleted = !(categories.get(CategoriesAdapter.selectedIndex).getItems().get(position).isCompleted());
                    categories.get(CategoriesAdapter.selectedIndex).getItems().get(position).setCompleted(isCompleted);
                    tasksAdapter.notifyItemChanged(position);
                    break;
            }
        }
    };

    private void dummyTesting() {
        ArrayList<Item> businessItems = new ArrayList<>();
        businessItems.add(new Item(1, "Meeting with Mark", true));
        businessItems.add(new Item(2, "Meeting with Philip", false));

        ArrayList<Item> homeItems = new ArrayList<>();
        homeItems.add(new Item(1, "Go to grocery store", true));
        homeItems.add(new Item(2, "Prepare the meal", false));
        homeItems.add(new Item(3, "Cook the meal", false));
        categories.add(new Category("Business", 20, 4, businessItems));

        categories.add(new Category("Home", 3, 1, homeItems));
    }

    private void configureAdapters() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tasksAdapter = new TasksAdapter(this, categories.get(CategoriesAdapter.selectedIndex).getItems());
        categoriesAdapter = new CategoriesAdapter(this, categories);

        binding.tasksRecyclerView.setAdapter(tasksAdapter);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);
        binding.categoriesRecyclerView.setLayoutManager(linearLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.tasksRecyclerView);

    }

    private void createAddTaskAlert() {
        taskTextField = new EditText(this);
        addNewTaskDialog = new AlertDialog.Builder(this);
        addNewTaskDialog.setTitle("Add a new Task");
        addNewTaskDialog.setMessage("Please enter the name of the task.");
        addNewTaskDialog.setView(taskTextField);

        addNewTaskDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = categories.get(CategoriesAdapter.selectedIndex).getItems().size() - 1;
                categories.get(CategoriesAdapter.selectedIndex).getItems().add(new Item(3, taskTextField.getText().toString(), false));
                tasksAdapter.notifyDataSetChanged();
            }
        });

        addNewTaskDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
}