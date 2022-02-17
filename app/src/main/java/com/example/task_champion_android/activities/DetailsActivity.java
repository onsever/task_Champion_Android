package com.example.task_champion_android.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.AudioItemsAdapter;
import com.example.task_champion_android.adapters.TaskImageAdapter;
import com.example.task_champion_android.databinding.ActivityDetailsBinding;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.db.MediaItem;
import com.example.task_champion_android.helper.PathUtilHelper;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DetailsActivity extends AppCompatActivity implements TaskImageAdapter.ItemClickListener {

    private List<MediaItem> imageList;
    private List<MediaItem> audioList;
    private ActivityDetailsBinding binding;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private AudioManager audioManager;
    private String filePath = "";

    private Boolean isRecording = false;
    private Boolean isPlayer = false;

    private Item selectedItem;

    private long itemId;

    private Boolean importAudio = false;

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
        hideStatusBar();
        Intent intent = getIntent();
        String _item = intent.getStringExtra("itemId");
        itemId = Long.parseLong(_item);
        configureAdapters();
        requestPermission();
        categoryViewModel.getSelectedItem(itemId).observe(this, item -> {
            selectedItem = item;
            long itemId = selectedItem.getId();
            categoryViewModel.getMediaByItemId(itemId).observe(this, mediaItems -> {
                Predicate<MediaItem> isAudio = newItem -> newItem.getType().equals(MediaItem.Type.AUDIO);
                Predicate<MediaItem> isImage = imageItem -> imageItem.getType().equals(MediaItem.Type.IMAGE);
                audioList = mediaItems.stream().filter(isAudio).collect(Collectors.toList());
                imageList = mediaItems.stream().filter(isImage).collect(Collectors.toList());
                if(checkDevicePermission()){
                    taskImageAdapter = new TaskImageAdapter(this, imageList, this);
                    binding.taskImagesRecyclerView.setAdapter(taskImageAdapter);
                }else {
                    requestPermission();
                    if(checkDevicePermission()){
                        taskImageAdapter = new TaskImageAdapter(this, imageList, this);
                        binding.taskImagesRecyclerView.setAdapter(taskImageAdapter);
                    }
                }
                audioItemsAdapter = new AudioItemsAdapter(this, audioList);
                binding.audioRecyclerView.setAdapter(audioItemsAdapter);
            });
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.audioRecyclerView.setLayoutManager(linearLayoutManager);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent intent = result.getData();
                    Uri fileUri = intent.getData();
                    String realPath = PathUtilHelper.getRealPath(getApplicationContext(),fileUri);
                    addNewAudioFile(realPath);

                }
            }
        });
        binding.importPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importImage();
            }
        });
        binding.recordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (!isRecording) {
                        startRecording();
                    } else {
                        stopRecording();
                    }
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

    }

    private void importImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }


    private void importAudio() {
        if(checkDevicePermission()) {
            Intent intent = new Intent();
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
            importAudio = true;
        }else {
            requestPermission();
        }
    }

    private void stopRecording() {
        if (!filePath.isEmpty()) {
            mediaRecorder.stop();
            mediaRecorder = null;
            addNewAudioFile(filePath);
            filePath = "";
            Toast.makeText(this, R.string.stop_recording, Toast.LENGTH_LONG).show();
            isRecording = false;
            binding.recordAudio.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24);
        }
    }

    public void removeAudioFile(MediaItem mediaItem) {
        categoryViewModel.deleteMediaItem(mediaItem);
    }

    public void playAudioFile(MediaItem mediaItem) {
        playAudio(mediaItem.getUri());
    }



    private void addNewAudioFile(String filePath) {
        String name = String.valueOf(importAudio?audioList.size()+1:imageList.size()+1);
        MediaItem item = new MediaItem();
        item.setName(name);
        item.setUri(filePath);
        item.setItemId(selectedItem.getId());
        item.setType(importAudio?MediaItem.Type.AUDIO:MediaItem.Type.IMAGE);
        categoryViewModel.insertMediaItem(item);
        audioItemsAdapter.updateItems();
    }


    private void startRecording() {
        if(checkDevicePermission()){
            String path = Environment.getExternalStorageDirectory() + File.separator;
            filePath = path + audioList.size()+1 +".3gpp";
            setUpRecorder(filePath);
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, R.string.start_recording, Toast.LENGTH_LONG).show();
                isRecording = true;
                binding.recordAudio.setImageResource(R.drawable.ic_recording_24);
            }catch (IllegalStateException | IOException ise) {
                ise.printStackTrace();
            }
        }else {
            requestPermission();
        }
    }

    private void playAudio (String path) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        );
        try{

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void setUpRecorder(String path) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(path);
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

    private void configureAdapters() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        taskImageAdapter = new TaskImageAdapter(this, new ArrayList<>(), this);
        binding.taskImagesRecyclerView.setAdapter(taskImageAdapter);
        binding.taskImagesRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void deleteDialog(MediaItem mediaItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you want to delete this photo?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            categoryViewModel.deleteMediaItem(mediaItem);
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onItemLongClick(MediaItem mediaItem) {
        deleteDialog(mediaItem);
    }
}