package com.example.task_champion_android.db;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "image_table",
        indices = {@Index("item_id")},
        foreignKeys = @ForeignKey(entity = Item.class,
        parentColumns = "id",
        childColumns = "item_id",
        onDelete = CASCADE)
)
public class Media {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="uri")
    private String uri;

    @ColumnInfo(name="item_id")
    private long itemId;

    @ColumnInfo(name="type")
    private Type type;

    @Ignore
    public Media() {

    }

    public Media(@NonNull String name, @NonNull String uri, @NonNull long itemId, @NonNull Type type){
        this.name = name;
        this.uri = uri;
        this.itemId = itemId;
        this.type = type;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        IMAGE,AUDIO
    }
}
