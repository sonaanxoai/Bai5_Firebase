package com.example.nhom4_w92;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserStatus;
    Button btnGoToLogin, btnViewProducts, btnViewCategories, btnViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_w92);

        tvUserStatus = findViewById(R.id.tvUserStatus);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnViewProducts = findViewById(R.id.btnViewProducts);
        btnViewCategories = findViewById(R.id.btnViewCategories);
        btnViewDetail = findViewById(R.id.btnViewDetailPlaceholder);

        updateUI();

        // 1. Login
        btnGoToLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        // 2. Xem danh sách Products
        btnViewProducts.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));

        // 3. Xem Categories
        btnViewCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryListActivity.class)));

        // 4. Xem chi tiết Product (Bấm vào đây để chọn sản phẩm muốn xem chi tiết)
        btnViewDetail.setOnClickListener(v -> {
            // Theo luồng: Bấm xem chi tiết -> dẫn đến danh sách để người dùng "Chọn sản phẩm"
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("fromViewDetail", true);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        SharedPreferences prefs = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "Guest");
        tvUserStatus.setText("Welcome, " + username);
    }
}
