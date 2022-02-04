package com.example.task_champion_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task_champion_android.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Category> categories = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dummyTesting();
    }

    private void dummyTesting() {
        categories.add(new Category("Business", 20, 4));
        categories.add(new Category("Home", 3, 1));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        CategoriesAdapter adapter = new CategoriesAdapter(this, categories);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
    }
}