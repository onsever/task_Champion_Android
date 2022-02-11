package com.example.task_champion_android.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ImageDao{




    @Insert
    void insertImage(Image image);

    @Query("Select * from Image")
    List<Image> getAllImage();

    @Update
    void updateImage(Image image);

    @Delete
    void deleteImage(Image image);
}
