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

    public LiveData<List<MediaItem>> getMediaByItemId (long id) {return repository.getMediasByItemId(id);}

    public LiveData<Item> getSelectedItem(long id) { return repository.getSelectedItem(id); }

    public LiveData<List<Item>> searchItemByName(long catID, String name) {
        return repository.searchItemByName(catID, name);
    }

    public LiveData<List<Item>> searchItemByCatID(long catId) {
        return repository.searchItemByCatID(catId);
    }

    public void insertItemToCategory(Category category, Item item) {
        repository.insertItem(category, item);
    }

    public void insertMediaItem( MediaItem mediaItem) {
        repository.insertMediaItem(mediaItem);
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
