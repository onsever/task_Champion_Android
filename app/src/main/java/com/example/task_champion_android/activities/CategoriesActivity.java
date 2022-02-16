package com.example.task_champion_android.activities;



import static com.example.task_champion_android.db.AppDatabase.INSTANCE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_champion_android.R;
import com.example.task_champion_android.adapters.catActivity_Adapter;
//import com.example.task_champion_android.catActivity_Adapter;
import com.example.task_champion_android.databinding.ActivityCategoriesBinding;
import com.example.task_champion_android.db.Category;
import com.example.task_champion_android.db.Item;
import com.example.task_champion_android.db.QueryDao;
import com.example.task_champion_android.viewmodel.CategoryViewModel;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    private final ArrayList<catActivityData> categories = new ArrayList<>();
    private ActivityCategoriesBinding binding;
    private AlertDialog.Builder addNewTaskDialog;
    private EditText taskTextField;
    private CategoryViewModel categoryViewModel;
    private catActivity_Adapter catActivity_adapter;
    private long categoryId;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dummyTesting();

    }

    private void dummyTesting() {
     /*   categories.add(new catActivityData("Business", R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("School",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Home",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Friends",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Family",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Hangout",R.drawable.ic_baseline_category_24));
        categories.add(new catActivityData("Work",R.drawable.ic_baseline_category_24));
*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        catActivity_Adapter adapter = new catActivity_Adapter(this, categories);
        binding.categ.setAdapter(adapter);
        binding.categ.setLayoutManager(linearLayoutManager);

        binding.btnAddNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNewTaskAlert();
            }
        });

    }
    private void createNewTaskAlert() {
        taskTextField = new EditText(this);
        addNewTaskDialog = new AlertDialog.Builder(this);
        addNewTaskDialog.setTitle("Add a new Category");
        addNewTaskDialog.setMessage("Please enter the name of the category.");
        addNewTaskDialog.setView(taskTextField);

        addNewTaskDialog.setPositiveButton("Add", (dialog,i) -> {
            String catName = taskTextField.getText().toString();

            if (TextUtils.isEmpty(catName)) {
                return;
            }

                Category category = new Category(catName);
                 categoryViewModel.insertCategory(category);




            dialog.dismiss();
        });

        addNewTaskDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        addNewTaskDialog.show();
    }


}