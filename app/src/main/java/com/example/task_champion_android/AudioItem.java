package com.example.task_champion_android;

import android.net.Uri;

public class AudioItem {
    private String name;
    private Uri path;

    public AudioItem(String name, Uri path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public Uri getPath() {
        return path;
    }
}
