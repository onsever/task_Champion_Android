package com.example.task_champion_android.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.ImageRecycleviewAdapter;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;
import com.example.task_champion_android.db.DatabaseProviderImg;
import com.example.task_champion_android.db.Image;
import com.example.task_champion_android.supportClass.ImageBitmapString;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    Bitmap bitmap =null ;
    int SELECT_PICTURE = 200;
    ImageView imageView;
    private ArrayList<Object> bitmaps;
    byte [] imageSources;

    final int CAMERA_INTENT = 51;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageView = findViewById(R.id.image_view);
    }

    public void upload(View view){loadImageFromGallary();}

    private void loadImageFromGallary() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);
    }

    public void save(View view){
        Image image = new Image();
        image.setImage(ImageBitmapString.getStringFromBitmap(bitmap));
        //image.setImage(ImageBitmapString.getStringFromBitmap(bitmap));
        DatabaseProviderImg.getDbConnection(getApplicationContext()).imageDao().insertImage(image);
    }

    public void getImage(View view){
        startActivity(new Intent(DetailsActivity.this,ImageRecycler.class));
    }


//    private void getImages()
//    {
//        mImageUrls.add("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        mImageUrls.add("https://media.istockphoto.com/photos/panorama-of-dubai-marina-in-uae-modern-skyscrapers-and-port-with-picture-id1266923176?b=1&k=20&m=1266923176&s=170667a&w=0&h=BAcB2chj9gQJystJETo24W2MAZZSe03NW5b0f-475D0=");
//        initRecyclerView();
//    }

//    private void initRecyclerView()
//    {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(layoutManager);
//        ImageRecycleviewAdapter adapter = new ImageRecycleviewAdapter(this,mImageUrls);
//        recyclerView.setAdapter(adapter);
//    }

//    public void viewImage(View view) {
//        Intent intent = new Intent(DetailsActivity.this,)
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(intent.resolveActivity(getPackageManager())!= null) {
//            //startActivityForResult(intent,CAMERA_INTENT);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Uri selectedImageRri = data.getData();
                if(null != selectedImageRri){
                    imageView.setImageURI(selectedImageRri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageRri);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}