package com.example.task_champion_android;

public class AudioItem {
    private String name;
    private String path;

    public AudioItem(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
