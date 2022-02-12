package com.example.task_champion_android.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class QueryDao {

    @Query("SELECT * FROM category_table")
    public abstract List<Category> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCategory(Category category);

    @Update
    public abstract void updateCategory(Category category);

    @Delete
    public abstract void deleteCategory(Category category);

    @Query("SELECT * FROM item_table WHERE categoryId = :categoryId")
    public abstract List<Item> getAllItems(int categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertItem(Item item);

    @Update
    public abstract  void updateItem(Item item);

    @Delete
    public abstract void deleteItem(Item item);

    @Query("DELETE FROM category_table")
    public abstract void deleteAllCategories();

    @Query("DELETE FROM item_table")
    public abstract void deleteAllItems();

}
