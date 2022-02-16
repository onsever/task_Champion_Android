package com.example.task_champion_android.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "itemName")
    private String itemName;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

    @ColumnInfo(name = "isCompleted")
    private boolean isCompleted;

    public Item(String itemName, int categoryId, boolean isCompleted) {
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
