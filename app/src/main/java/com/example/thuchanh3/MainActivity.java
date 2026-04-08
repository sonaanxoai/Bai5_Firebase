package com.example.thuchanh3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_th3);

        Button btnInventory = findViewById(R.id.btnViewInventory);
        Button btnTransaction = findViewById(R.id.btnNewTransaction);
        Button btnHistory = findViewById(R.id.btnHistory);

        btnInventory.setOnClickListener(v -> startActivity(new Intent(this, InventoryActivity.class)));
        btnTransaction.setOnClickListener(v -> startActivity(new Intent(this, TransactionActivity.class)));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
    }
}
