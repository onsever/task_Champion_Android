package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.task_champion_android.databinding.ActivityCategoriesBinding;

public class CategoriesActivity extends AppCompatActivity {

    private ActivityCategoriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}