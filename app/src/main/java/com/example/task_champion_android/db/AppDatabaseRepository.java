package com.example.task_champion_android.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppDatabaseRepository {

    private QueryDao queryDao;
    private LiveData<List<Category>> categories;
    private LiveData<List<CategoryWithItems>> items;
    private LiveData<Item> selectedItem;
    private LiveData<List<ItemWithMedias>> mediaItems;

    public AppDatabaseRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        queryDao = db.queryDao();
        categories = queryDao.getAllCategories();
        items = queryDao.getCategoryWithItems();
        mediaItems = queryDao.getItemWithMedias();
    }

    public LiveData<List<Category>> getCategories() {return categories;}
    public LiveData<List<CategoryWithItems>> getItems() {return items;}

    public LiveData<Item> getSelectedItem(long id) { return queryDao.getSelectedItem(id);}

    public LiveData<List<ItemWithMedias>> getMediaItems() {return mediaItems;}

    public void insertCategory(Category category) {
        AppDatabase.databaseWriteExecutor.execute(()->queryDao.insertCategory(category));
    }

    public void insertItem(Category category, Item item) {
        AppDatabase.databaseWriteExecutor.execute(()->queryDao.insertItem(category,item));
    }

    public void insertMediaItem(Category category,Item item, MediaItem mediaItem) {
        AppDatabase.databaseWriteExecutor.execute(()->queryDao.insertMediaItem(category,item,mediaItem));
    }

    public void updateItem(Category category, Item item) {
        AppDatabase.databaseWriteExecutor.execute(()->queryDao.updateItem(category,item));
    }

    public void updateMediaItem(Category category,Item item, MediaItem mediaItem){
        AppDatabase.databaseWriteExecutor.execute(()->queryDao.updateMediaItem(category,item,mediaItem));
    }

    public void deleteMediaItem(MediaItem mediaItem) {
        AppDatabase.databaseWriteExecutor.execute(()->queryDao.deleteMediaItem(mediaItem));
    }

    public void updateCategory(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            queryDao.updateCategory(category);
        });
    }

    public void deleteCategory(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            queryDao.deleteCategory(category);
        });
    }

}
