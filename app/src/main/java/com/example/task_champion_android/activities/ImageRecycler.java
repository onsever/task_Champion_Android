package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.ImageRecycleviewAdapter;
import com.example.task_champion_android.db.DatabaseProviderImg;
import com.example.task_champion_android.db.Image;

import java.util.List;

public class ImageRecycler extends AppCompatActivity {
    RecyclerView  recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recycler);

        recyclerView = findViewById(R.id.rr);
        List<Image> image = DatabaseProviderImg.getDbConnection(getApplicationContext()).imageDao().getAllImage();
        ImageRecycleviewAdapter imageRecycleviewAdapter = new ImageRecycleviewAdapter(getApplicationContext(),image);
    }
}