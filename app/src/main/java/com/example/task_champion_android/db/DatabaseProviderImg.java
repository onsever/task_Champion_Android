package com.example.task_champion_android.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.task_champion_android.R;
import com.example.task_champion_android.supportClass.ImageBitmapString;

@Database(entities = {Image.class},version = 1)
@TypeConverters({ImageBitmapString.class})
public abstract class DatabaseProviderImg extends RoomDatabase {

    public abstract ImageDao imageDao();

    public static DatabaseProviderImg databaseProvider = null;
    public static DatabaseProviderImg getDbConnection(Context context){
        if(databaseProvider == null){
            databaseProvider = Room.databaseBuilder(context.getApplicationContext(),DatabaseProviderImg.class,"newdb").allowMainThreadQueries().build();
        }
        return databaseProvider;
    }
}
