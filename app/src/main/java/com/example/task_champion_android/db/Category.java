package com.example.task_champion_android.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")


public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    public Category () {

    }

    public Category(String name) {
        this.name = name;
    }



    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setCategoryName(String name) {
        this.name = name;
    }

}
