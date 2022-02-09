package com.example.task_champion_android.db;

import androidx.room.Entity;

@Entity
public class Image {
    Byte [] image;

    public Byte[] getImage() {
        return image;
    }
    public void setImage(Byte[] image) {
        this.image = image;
    }
}
