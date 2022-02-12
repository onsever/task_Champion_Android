package com.example.task_champion_android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.task_champion_android.db.AppDatabase;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private MutableLiveData<List<Item>> items;
    private AppDatabase appDatabase;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        items = new MutableLiveData<>();

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Item>> getItemsList() {
        return items;
    }

    public void getAllItemList(int categoryId) {
        List<Item> itemList = appDatabase.queryDao().getAllItems(categoryId);

        if (itemList.size() > 0) {
            items.postValue(itemList);
        } else {
            items.postValue(null);
        }
    }

    public void insertItem(Item item) {
        appDatabase.queryDao().insertItem(item);
        getAllItemList(item.getCategoryId());
    }

    public void updateItem(Item item) {
        appDatabase.queryDao().updateItem(item);
        getAllItemList(item.getCategoryId());
    }

    public void deleteItem(Item item) {
        appDatabase.queryDao().deleteItem(item);
        getAllItemList(item.getCategoryId());
    }

    public void deleteAllItems() {
        appDatabase.queryDao().deleteAllItems();
    }

    public List<Item> getTestItems(int categoryId) {
        return appDatabase.queryDao().getAllItems(categoryId);
    }

}
