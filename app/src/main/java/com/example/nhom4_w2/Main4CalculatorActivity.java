package com.example.nhom4_w2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Main4CalculatorActivity extends AppCompatActivity {

    EditText edtNum1, edtNum2, edtResult;
    Button btnAdd, btnSub, btnMul, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_calculator); // đổi theo layout của bạn

        // Ánh xạ view
        edtNum1 = findViewById(R.id.edtNum1);
        edtNum2 = findViewById(R.id.edtNum2);
        edtResult = findViewById(R.id.edtResult);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        // Cộng
        btnAdd.setOnClickListener(v -> {
            Double[] nums = getNumbers();
            if (nums == null) return;
            edtResult.setText(String.valueOf(nums[0] + nums[1]));
        });

        // Trừ
        btnSub.setOnClickListener(v -> {
            Double[] nums = getNumbers();
            if (nums == null) return;
            edtResult.setText(String.valueOf(nums[0] - nums[1]));
        });

        // Nhân
        btnMul.setOnClickListener(v -> {
            Double[] nums = getNumbers();
            if (nums == null) return;
            edtResult.setText(String.valueOf(nums[0] * nums[1]));
        });

        // Chia
        btnDiv.setOnClickListener(v -> {
            Double[] nums = getNumbers();
            if (nums == null) return;

            if (nums[1] == 0) {
                Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
            } else {
                edtResult.setText(String.valueOf(nums[0] / nums[1]));
            }
        });
    }

    private Double[] getNumbers() {
        String n1 = edtNum1.getText().toString().trim();
        String n2 = edtNum2.getText().toString().trim();

        if (n1.isEmpty() || n2.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ 2 số", Toast.LENGTH_SHORT).show();
            return null;
        }

        try {
            double num1 = Double.parseDouble(n1);
            double num2 = Double.parseDouble(n2);
            return new Double[]{num1, num2};
        } catch (Exception e) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
