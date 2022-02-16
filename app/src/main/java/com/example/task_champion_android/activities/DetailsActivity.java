package com.example.task_champion_android.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.task_champion_android.AudioItem;
import com.example.task_champion_android.AudioItemsAdapter;
import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.TaskImageAdapter;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class DetailsActivity extends AppCompatActivity {

    private final ArrayList<AudioItem> itemList = new ArrayList<>();
    private ActivityDetailsBinding binding;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private AudioManager audioManager;
    private String filePath = "";

    private Item selectedItem;

    private long itemId;

    private final int REQUEST_PERMISSION_CODE = 1;

    private AudioItemsAdapter audioItemsAdapter;

    ActivityResultLauncher<Intent> activityResultLauncher;

    private CategoryViewModel categoryViewModel;



    private TaskImageAdapter taskImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        categoryViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(CategoryViewModel.class);


        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String _item = intent.getStringExtra("itemId");
        itemId = Long.parseLong(_item);

        categoryViewModel.getSelectedItem(itemId).observe(this, item -> {
            selectedItem = item;
        });

        // Create dummy data
        itemList.add(new AudioItem("1",Uri.parse("/1/111/")));
        itemList.add(new AudioItem("2",Uri.parse("/1/222/")));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        audioItemsAdapter = new AudioItemsAdapter(this, itemList);
        binding.audioRecyclerView.setLayoutManager(linearLayoutManager);
        binding.audioRecyclerView.setAdapter(audioItemsAdapter);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Intent data = result.getData();
                    Uri fileUri = data.getData();
                    addNewAudioFile(fileUri);
                    Log.d("detailsActivity", "imported file path: " + fileUri);
//                    playAudio(fileUri);
                }
            }
        });
        binding.recordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });

        binding.importAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importAudio();
            }
        });
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                ,0);

        // Check permission
        if(!checkDevicePermission()){
            requestPermission();
        }
    }

    private void importAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }

    private void stopRecording() {
        if (!filePath.isEmpty()) {
            mediaRecorder.stop();
            mediaRecorder = null;
            Uri uri = Uri.parse(filePath);
            addNewAudioFile(uri);
            filePath = "";
            Toast.makeText(this, "Stop recording", Toast.LENGTH_LONG).show();
        }
    }

    private void addNewAudioFile(Uri filePath) {
        String name = String.valueOf(itemList.size()+1);
        AudioItem newItem = new AudioItem(name,filePath);
        itemList.add(newItem);
        audioItemsAdapter.updateItems();
    }


    private void startRecording() {
        if(checkDevicePermission()){
            filePath = getExternalCacheDir().getAbsolutePath() + UUID.randomUUID().toString();
            setUpRecorder();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, "start recording", Toast.LENGTH_LONG).show();
            }catch (IllegalStateException | IOException ise) {
                ise.printStackTrace();
            }
        }else {
            requestPermission();
        }
    }

    private void playAudio (Uri path) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        );
        try{
            mediaPlayer.setDataSource(getApplicationContext(), path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void setUpRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(filePath);
    }

    private void requestPermission () {
        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO},
                REQUEST_PERMISSION_CODE);
    }

    private boolean checkDevicePermission() {
        final int GRANTED_RES = PackageManager.PERMISSION_GRANTED;
        int WRITE_EXTERNAL_STORAGE_RES = ContextCompat
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int RECORD_AUDIO_RES = ContextCompat
                .checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return WRITE_EXTERNAL_STORAGE_RES == GRANTED_RES &&
                RECORD_AUDIO_RES == GRANTED_RES;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE) {
            Toast.makeText(this, grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ?
                    "Permission Granted":
                    "Permission Denied",
                    Toast.LENGTH_SHORT).show();
        }

        configureAdapters();
    }

    private void configureAdapters() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        taskImageAdapter = new TaskImageAdapter(this);
        binding.taskImagesRecyclerView.setAdapter(taskImageAdapter);
        binding.taskImagesRecyclerView.setLayoutManager(linearLayoutManager);

    }
}