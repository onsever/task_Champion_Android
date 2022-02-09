package com.example.task_champion_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.task_champion_android.AudioItem;
import com.example.task_champion_android.AudioItemsAdapter;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class DetailsActivity extends AppCompatActivity {

    private final ArrayList<AudioItem> itemList = new ArrayList<>();
    private ActivityDetailsBinding binding;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private AudioManager audioManager;
    private String filePath = "";

    private final int REQUEST_PERMISSION_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Create dummy data
        itemList.add(new AudioItem("1","/1/111/"));
        itemList.add(new AudioItem("2","/1/111/"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AudioItemsAdapter audioItemsAdapter = new AudioItemsAdapter(this, itemList);
        binding.audioRecyclerView.setLayoutManager(linearLayoutManager);
        binding.audioRecyclerView.setAdapter(audioItemsAdapter);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        // Check permission
        if(!checkDevicePermission()){
            requestPermission();
        }
    }

    private void startRecording() {
        if(checkDevicePermission()){
            filePath = getExternalCacheDir().getAbsolutePath() + UUID.randomUUID().toString();
            setUpRecorder();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            }catch (IllegalStateException | IOException ise) {
                ise.printStackTrace();
            }
        }else {
            requestPermission();
        }
    }

    private void playAudio (String path) {
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
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
    }
}