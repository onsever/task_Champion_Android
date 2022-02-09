package com.example.task_champion_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.ImageRecycleviewAdapter;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getImages();
    }

    private void getImages()
    {
        mImageUrls.add("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
        initRecyclerView();
    }

    private void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ImageRecycleviewAdapter adapter = new ImageRecycleviewAdapter(this,mImageUrls);
        recyclerView.setAdapter(adapter);
    }

}