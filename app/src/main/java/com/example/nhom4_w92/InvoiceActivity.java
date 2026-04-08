package com.example.nhom4_w92;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    TextView tvOrderId, tvUser, tvDate, tvTotal;
    ListView lvItems;
    Button btnBackHome;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_w92);

        tvOrderId = findViewById(R.id.tvInvoiceOrderId);
        tvUser = findViewById(R.id.tvInvoiceUser);
        tvDate = findViewById(R.id.tvInvoiceDate);
        tvTotal = findViewById(R.id.tvInvoiceTotal);
        lvItems = findViewById(R.id.lvInvoiceItems);
        btnBackHome = findViewById(R.id.btnBackToHome);
        db = AppDatabase.getDatabase(this);

        int orderId = getIntent().getIntExtra("orderId", -1);
        Order order = db.appDao().getOrderById(orderId);

        if (order != null) {
            User user = db.appDao().getUserById(order.userId);
            tvOrderId.setText("Mã hóa đơn: #" + order.orderId);
            tvUser.setText("Khách hàng: " + (user != null ? user.username : "Ẩn danh"));
            tvDate.setText("Ngày đặt: " + order.orderDate);

            List<OrderDetail> details = db.appDao().getOrderDetailsByOrder(orderId);
            double total = 0;
            List<String> displayList = new ArrayList<>();

            for (OrderDetail d : details) {
                Product p = db.appDao().getProductById(d.productId);
                String itemName = p != null ? p.productName : "Sản phẩm #" + d.productId;
                displayList.add(String.format("%s (x%d) - %,.0f VND", itemName, d.quantity, d.unitPrice * d.quantity));
                total += d.unitPrice * d.quantity;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
            lvItems.setAdapter(adapter);
            tvTotal.setText(String.format("Tổng thanh toán: %,.0f VND", total));
        }

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
