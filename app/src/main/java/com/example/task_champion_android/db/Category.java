package com.example.task_champion_android.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "categoryName")
    private String categoryName;
    private int itemCounter;

    public Category(String categoryName, int itemCounter) {
        this.categoryName = categoryName;
        this.itemCounter = itemCounter;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getItemCounter() {
        return itemCounter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setItemCounter(int itemCounter) {
        this.itemCounter = itemCounter;
    }
}
