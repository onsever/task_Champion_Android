package com.example.task_champion_android.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ItemWithMedias {
    @Embedded public Item item;
    @Relation(
            parentColumn = "id",
            entityColumn = "item_id"
    )

    public List<Media> mediaList;

    public ItemWithMedias(Item item, List<Media> mediaList){
        this.item = item;
        this.mediaList = mediaList;
    }

    public Item getItem() {return item;}
    public List<Media> getMediaList() {return mediaList;}
    public int getMediaListSize() {return mediaList == null ? 0 : mediaList.size();}
}
