package com.example.nhom4_w92;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    TextView tvName, tvPrice, tvDesc;
    Button btnAddToCart;
    AppDatabase db;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_w92);

        tvName = findViewById(R.id.tvDetailProductName);
        tvPrice = findViewById(R.id.tvDetailProductPrice);
        tvDesc = findViewById(R.id.tvDetailProductDesc);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        db = AppDatabase.getDatabase(this);

        int productId = getIntent().getIntExtra("productId", -1);
        product = db.appDao().getProductById(productId);

        if (product != null) {
            tvName.setText(product.productName);
            // Định dạng giá tiền cho đẹp (không bị 2.5E7)
            tvPrice.setText(String.format("Giá: %,.0f VND", product.price));
            tvDesc.setText(product.description);
        }

        btnAddToCart.setOnClickListener(v -> handleAddToCart());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE);
        boolean pendingAdd = prefs.getBoolean("pendingAddProduct", false);
        int pendingId = prefs.getInt("pendingProductId", -1);

        if (pendingAdd && pendingId == (product != null ? product.productId : -1) && prefs.getInt("userId", -1) != -1) {
            prefs.edit().remove("pendingAddProduct").remove("pendingProductId").apply();
            performAddToCart();
        }
    }

    private void handleAddToCart() {
        SharedPreferences prefs = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId == -1) {
            prefs.edit()
                    .putBoolean("pendingAddProduct", true)
                    .putInt("pendingProductId", product.productId)
                    .apply();
            
            Toast.makeText(this, "Vui lòng đăng nhập để nhặt hàng!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        performAddToCart();
    }

    private void performAddToCart() {
        SharedPreferences prefs = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        
        Order pendingOrder = db.appDao().getPendingOrderByUser(userId);
        int orderId;
        if (pendingOrder == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            orderId = (int) db.appDao().insertOrder(new Order(userId, date, "Pending"));
        } else {
            orderId = pendingOrder.orderId;
        }

        db.appDao().insertOrderDetail(new OrderDetail(orderId, product.productId, 1, product.price));
        Toast.makeText(this, "Đã nhặt sản phẩm vào giỏ!", Toast.LENGTH_SHORT).show();

        // Luồng: Có tiếp tục chọn sản phẩm?
        new AlertDialog.Builder(this)
                .setTitle("Tiếp tục mua sắm?")
                .setMessage("Bạn có muốn tiếp tục chọn sản phẩm khác không?")
                .setPositiveButton("Tiếp tục (Yes)", (dialog, which) -> {
                    // Quay lại danh sách sản phẩm
                    Intent intent = new Intent(this, ProductListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Thanh toán (No)", (dialog, which) -> {
                    // Chuyển đến Checkout (CartActivity)
                    startActivity(new Intent(this, CartActivity.class));
                    finish();
                })
                .show();
    }
}
