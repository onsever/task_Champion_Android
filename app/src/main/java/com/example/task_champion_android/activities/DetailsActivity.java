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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
    final int SELECT_PICTURE = 200;
    ImageView imageView;
    private ArrayList<Object> bitmaps;
    byte [] imageSources;

    final int CAMERA_INTENT = 51;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

    }

    public void upload(View view){loadImageFromGallary();}

    private void loadImageFromGallary() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);
    }


    public void getImage(View view){
        Image image = new Image();
        image.setImage(ImageBitmapString.getStringFromBitmap(bitmap));
        DatabaseProviderImg.getDbConnection(getApplicationContext()).imageDao().insertImage(image);
        startActivity(new Intent(DetailsActivity.this,ImageRecycler.class));
    }


    private void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ImageRecycleviewAdapter adapter = new ImageRecycleviewAdapter(this,bitmaps);
        recyclerView.setAdapter(adapter);
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!= null) {
            startActivityForResult(intent,CAMERA_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SELECT_PICTURE:

                if(resultCode == RESULT_OK){
                    if (requestCode == SELECT_PICTURE){
                        Log.d("Read text",data.getData().toString());
                        Uri selectedImageRri = data.getData();
                        if(null != selectedImageRri){
                            //imageView.setImageURI(selectedImageRri);
                            Glide.with(this).asBitmap().load(data).listener(new RequestListener<Bitmap>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                    return true;
                                }
                            }).submit();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageRri);
                                Glide.with(this).asBitmap().load(data).listener(new RequestListener<Bitmap>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                        return true;
                                    }
                                }).submit();
//                                imageView.setImageBitmap(bitmap);
                                initRecyclerView();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                break;

            case CAMERA_INTENT:
               if(requestCode == Activity.RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if(bitmap!= null){
                        imageView.setImageBitmap(bitmap);
                        initRecyclerView();
                    }
                    else {
                        Toast.makeText(this,"Bitmap is null",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"Result not ok",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}