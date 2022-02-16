package com.example.task_champion_android.db;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
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
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name="detail")
    private String detail;

    @ColumnInfo(name = "category_id")
    private long categoryId;


    @ColumnInfo(name = "isCompleted")
    private boolean isCompleted =false;


    @Ignore Item () {
    }

    public Item(@NonNull String name, @NonNull long categoryId, String detail, boolean isCompleted) {
        this.name = name;
        this.categoryId = categoryId;
        this.detail = detail;
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name;}

    public long getCategoryId() { return categoryId; }

    public void setCategoryId(long id) { this.categoryId = id;}

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean status) {this.isCompleted = status;}

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
