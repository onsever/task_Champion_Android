package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.ImageRecycleviewAdapter;
import com.example.task_champion_android.db.DatabaseProviderImg;
import com.example.task_champion_android.db.Image;
import com.example.task_champion_android.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ImageRecycler extends AppCompatActivity {
    RecyclerView  recyclerView;
    private ImageRecycleviewAdapter imageRecycleviewAdapter;
    private List<ItemViewModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recycler);

        recyclerView = findViewById(R.id.rr);
        list = new ArrayList<>();
        imageRecycleviewAdapter = new ImageRecycleviewAdapter(this,list);

        List<Image> image = DatabaseProviderImg.getDbConnection(getApplicationContext()).imageDao().getAllImage();
        ImageRecycleviewAdapter imageRecycleviewAdapter = new ImageRecycleviewAdapter(getApplicationContext(),image);

        recyclerView.setAdapter(imageRecycleviewAdapter);
    }
}