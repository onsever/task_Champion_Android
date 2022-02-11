package com.example.task_champion_android.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name = "image_id")
    private int id;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @ColumnInfo(name = "imageList", typeAffinity = ColumnInfo.BLOB)
    private byte [] image;
    public int getId(){return id;}
    public void setId(int id){this.id = id; }

}
