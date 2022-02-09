package com.example.task_champion_android.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QueryDao {

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM item_table WHERE categoryId = :categoryId")
    List<Item> getAllItems(int categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    @Update
    void updateItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM category_table")
    void deleteAllCategories();

    @Query("DELETE FROM item_table")
    void deleteAllItems();

}
