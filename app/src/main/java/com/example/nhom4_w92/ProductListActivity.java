package com.example.nhom4_w92;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ListView lvProducts;
    AppDatabase db;
    List<Product> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_w92);

        lvProducts = findViewById(R.id.lvProducts);
        db = AppDatabase.getDatabase(this);

        int categoryId = getIntent().getIntExtra("categoryId", -1);
        if (categoryId != -1) {
            productsList = db.appDao().getProductsByCategory(categoryId);
        } else {
            productsList = db.appDao().getAllProducts();
        }

        List<String> displayList = new ArrayList<>();
        for (Product p : productsList) {
            displayList.add(String.format("%s - %,.0f VND", p.productName, p.price));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvProducts.setAdapter(adapter);

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("productId", productsList.get(position).productId);
            startActivity(intent);
        });
    }
}
