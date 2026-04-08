package com.example.nhom4_w92;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    ListView lvCategories;
    AppDatabase db;
    List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_w92);

        lvCategories = findViewById(R.id.lvCategories);
        db = AppDatabase.getDatabase(this);
        categories = db.appDao().getAllCategories();

        List<String> displayList = new ArrayList<>();
        for (Category c : categories) {
            displayList.add(c.categoryName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvCategories.setAdapter(adapter);

        lvCategories.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("categoryId", categories.get(position).categoryId);
            startActivity(intent);
        });
    }
}
