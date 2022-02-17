package com.example.task_champion_android.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class QueryDao {

    @Query("SELECT * FROM media_table WHERE item_id=:itemId")
    public abstract LiveData<List<MediaItem>> getMediaItems(long itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertMediaItem (MediaItem mediaItem);

    @Update
    public abstract void updateMediaItem(MediaItem mediaItem);

    @Delete
    public abstract void deleteMediaItem(MediaItem mediaItem);

    @Query("DELETE FROM category_table")
    public abstract void deleteAll();

    @Transaction
    public void updateMediaItem(Category category, Item item, MediaItem mediaItem){
        updateItem(category,item);
        updateMediaItem(mediaItem);
    }

    @Transaction
    @Query("SELECT * FROM item_table")
    public abstract LiveData<List<ItemWithMedias>> getItemWithMedias();

    @Query("SELECT * FROM item_table WHERE id = :itemId")
    public abstract LiveData<Item> getSelectedItem (long itemId);

    @Query("SELECT * FROM item_table WHERE category_id = :categoryId and LOWER(name) LIKE '%' || LOWER(:name) || '%'")
    public abstract LiveData<List<Item>> searchItemByName(long categoryId, String name);

    @Query("SELECT * FROM item_table WHERE category_id = :categoryId")
    public abstract LiveData<List<Item>> getAllItems(long categoryId);

    @Query("SELECT * FROM item_table WHERE category_id = :categoryId ORDER BY name ASC")
    public abstract LiveData<List<Item>> getAllItemsSortedByName(long categoryId);

    @Query("SELECT * FROM item_table WHERE category_id = :categoryId ORDER BY created_at DESC")
    public abstract LiveData<List<Item>> getAllItemsSortedByDate(long categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertItem(Item item);

    @Update
    public abstract void updateItem(Item item);

    @Delete
    public abstract void deleteItem(Item item);

    @Query("DELETE FROM item_table")
    public abstract void deleteAllItems();

    @Transaction
    public void insertItem(Category category, Item item) {
        final long catId = category.getId();
        item.setCategoryId(catId);
        insertItem(item);
    }

    @Transaction
    public void updateItem(Category category, Item item){
        updateCategory(category);
        updateItem(item);
    }

    @Transaction
    @Query("SELECT * FROM category_table")
    public abstract LiveData<List<CategoryWithItems>> getCategoryWithItems();


    @Query("SELECT * FROM category_table ORDER BY name ASC")
    public abstract LiveData<List<Category>> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertCategory(Category category);

    @Update
    public abstract void updateCategory(Category category);

    @Delete
    public abstract void deleteCategory(Category category);

    @Query("DELETE FROM category_table")
    public abstract void deleteAllCategories();

}
