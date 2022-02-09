package com.example.task_champion_android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.task_champion_android.db.AppDatabase;
import com.example.task_champion_android.db.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private MutableLiveData<List<Category>> categories;
    private AppDatabase appDatabase;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categories = new MutableLiveData<>();

        appDatabase = AppDatabase.getInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        return categories;
    }

    public void getAllCategoryList() {
        List<Category> categoryList = appDatabase.queryDao().getAllCategories();

        if (categoryList.size() > 0) {
            categories.postValue(categoryList);
        } else {
            categories.postValue(null);
        }
    }

    public void insertCategory(String categoryName) {
        Category category = new Category(categoryName, 0);
        appDatabase.queryDao().insertCategory(category);
        getAllCategoryList();
    }

    public void updateCategory(Category category) {
        appDatabase.queryDao().updateCategory(category);
        getAllCategoryList();
    }

    public void deleteCategory(Category category) {
        appDatabase.queryDao().deleteCategory(category);
        getAllCategoryList();
    }

    public void deleteAllCategories() {
        appDatabase.queryDao().deleteAllCategories();
        getAllCategoryList();
    }

}
