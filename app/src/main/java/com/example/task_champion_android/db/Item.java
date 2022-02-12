package com.example.task_champion_android.db;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table",
        indices = {@Index("category_id")},
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = CASCADE
        )
)

public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "category_id")
    private long categoryId;


    @ColumnInfo(name = "isCompleted")
    private boolean isCompleted;


    @Ignore Item () {
    }

    public Item(String name, long categoryId, boolean isCompleted) {
        this.name = name;
        this.categoryId = categoryId;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getCategoryId() { return categoryId; }

    public boolean isCompleted() {
        return isCompleted;
    }
}
