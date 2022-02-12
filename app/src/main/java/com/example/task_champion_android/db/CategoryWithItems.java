package com.example.task_champion_android.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithItems {
    @Embedded public Category category;
    @Relation(
            parentColumn = "id",
            entityColumn = "category_id"
    )

    public List<Item> itemList;

    public CategoryWithItems(Category category, List<Item> itemList) {
        this.category = category;
        this.itemList = itemList;
    }

    public Category getCategory() { return  category;}

    public List<Item> getItemList() { return itemList;}

    public int getItemListSize() {return itemList == null ? 0 : itemList.size();}
}
