package com.example.nhom4_w92;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ListView lvCartItems;
    TextView tvTotal;
    Button btnCheckout;
    AppDatabase db;
    Order pendingOrder;
    List<OrderDetail> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_w92);

        lvCartItems = findViewById(R.id.lvCartItems);
        tvTotal = findViewById(R.id.tvTotalAmount);
        btnCheckout = findViewById(R.id.btnCheckout);
        db = AppDatabase.getDatabase(this);

        loadCart();

        btnCheckout.setOnClickListener(v -> {
            if (pendingOrder != null && details != null && !details.isEmpty()) {
                pendingOrder.status = "Paid";
                db.appDao().updateOrder(pendingOrder);
                Toast.makeText(this, "Order Paid Successfully!", Toast.LENGTH_LONG).show();
                
                Intent intent = new Intent(this, InvoiceActivity.class);
                intent.putExtra("orderId", pendingOrder.orderId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCart() {
        SharedPreferences prefs = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        pendingOrder = db.appDao().getPendingOrderByUser(userId);

        if (pendingOrder == null) {
            tvTotal.setText("Tổng cộng: 0 VND");
            return;
        }

        details = db.appDao().getOrderDetailsByOrder(pendingOrder.orderId);
        double total = 0;
        List<String> displayList = new ArrayList<>();
        
        for (OrderDetail d : details) {
            Product p = db.appDao().getProductById(d.productId);
            String prodName = p != null ? p.productName : "Sản phẩm #" + d.productId;
            displayList.add(String.format("%s (x%d) - %,.0f VND", prodName, d.quantity, d.unitPrice * d.quantity));
            total += d.unitPrice * d.quantity;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvCartItems.setAdapter(adapter);
        tvTotal.setText(String.format("Tổng cộng: %,.0f VND", total));
    }
}
