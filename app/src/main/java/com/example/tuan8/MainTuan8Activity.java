package com.example.tuan8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

public class MainTuan8Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tuan8);

        Button btnBai81 = findViewById(R.id.btnBai81);
        Button btnBai82 = findViewById(R.id.btnBai82);

        btnBai81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTuan8Activity.this, com.example.tuan8.LoginActivity.class);
                startActivity(intent);
            }
        });

        btnBai82.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainTuan8Activity.this, com.example.tuan82.LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
