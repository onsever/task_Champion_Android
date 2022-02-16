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

    public List<MediaItem> mediaItemList;

    public ItemWithMedias(Item item, List<MediaItem> mediaItemList){
        this.item = item;
        this.mediaItemList = mediaItemList;
    }

    public Item getItem() {return item;}
    public List<MediaItem> getMediaList() {return mediaItemList;}
    public int getMediaListSize() {return mediaItemList == null ? 0 : mediaItemList.size();}
}
