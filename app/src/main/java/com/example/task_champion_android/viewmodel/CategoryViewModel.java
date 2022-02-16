package com.example.task_champion_android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.task_champion_android.db.AppDatabase;
import com.example.task_champion_android.db.AppDatabaseRepository;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.CategoryWithItems;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.db.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private AppDatabaseRepository repository;
    private LiveData<List<Category>> categories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        repository = new AppDatabaseRepository(application);
        categories = repository.getCategories();

    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<List<CategoryWithItems>> getCategoryWithItems() {
        return repository.getItems();
    }

    public void insertItemToCategory(Category category, Item item) {
        repository.insertItem(category, item);
    }

    public void deleteItem(Item item) {
        repository.deleteItem(item);
    }

    public void insertMediaItem(Category category, Item item, MediaItem mediaItem) {
        repository.insertMediaItem(category, item, mediaItem);
    }

    public void updateItem(Category category, Item item) {
        repository.updateItem(category, item);
    }

    public void updateMediaItem(Category category, Item item, MediaItem mediaItem){
        repository.updateMediaItem(category, item, mediaItem);
    }

    public void deleteMediaItem(MediaItem mediaItem) {
        repository.deleteMediaItem(mediaItem);
    }

    public void insertCategory(Category category) {
        repository.insertCategory(category);
    }

    public void updateCategory(Category category) {
        repository.updateCategory(category);
    }

    public void deleteCategory(Category category) {
        repository.deleteCategory(category);
    }

}
