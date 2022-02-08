package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.task_champion_android.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}