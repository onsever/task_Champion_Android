package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.TaskImageAdapter;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private TaskImageAdapter taskImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureAdapters();

        binding.taskName.setText("Meeting with Mark");
    }

    private void configureAdapters() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        taskImageAdapter = new TaskImageAdapter(this);
        binding.taskImagesRecyclerView.setAdapter(taskImageAdapter);
        binding.taskImagesRecyclerView.setLayoutManager(linearLayoutManager);

    }
}