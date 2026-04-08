package com.example.nhom4_w2;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Bai2Activity extends AppCompatActivity {

    private EditText etFullName, etEmail;
    private RadioGroup rgGrade;
    private CheckBox cbBongBan, cbCauLong, cbCoVua;
    private Button btnRegister, btnCancel;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        // Ánh xạ view
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        rgGrade = findViewById(R.id.rgGrade);
        cbBongBan = findViewById(R.id.cbBongBan);
        cbCauLong = findViewById(R.id.cbCauLong);
        cbCoVua = findViewById(R.id.cbCoVua);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);
        tvResult = findViewById(R.id.tvResult);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void register() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // 1. Validate họ tên
        if (fullName.isEmpty()) {
            etFullName.setError("Họ tên không được để trống");
            return;
        }
        
        // Kiểm tra ít nhất 2 từ và chỉ chứa chữ cái
        String[] words = fullName.split("\\s+");
        if (words.length < 2) {
            etFullName.setError("Họ tên phải có ít nhất 2 từ");
            return;
        }
        if (!fullName.matches("^[\\p{L}\\s]+$")) {
            etFullName.setError("Họ tên chỉ được chứa chữ cái");
            return;
        }

        // 2. Validate email
        if (email.isEmpty()) {
            etEmail.setError("Email không được để trống");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email không đúng định dạng");
            return;
        }

        // 3. Kiểm tra số lượng môn tham gia (tối đa 2)
        List<String> selectedSports = new ArrayList<>();
        if (cbBongBan.isChecked()) selectedSports.add("Bóng bàn");
        if (cbCauLong.isChecked()) selectedSports.add("Cầu lông");
        if (cbCoVua.isChecked()) selectedSports.add("Cờ vua");

        if (selectedSports.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất 1 môn thi đấu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedSports.size() > 2) {
            Toast.makeText(this, "Mỗi người chỉ được tham dự tối đa 2 nội dung", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin lớp
        int selectedId = rgGrade.getCheckedRadioButtonId();
        RadioButton rbGrade = findViewById(selectedId);
        String grade = rbGrade != null ? rbGrade.getText().toString() : "Chưa chọn";

        // Hiển thị kết quả
        StringBuilder result = new StringBuilder();
        result.append("--- THÔNG TIN ĐĂNG KÝ ---\n");
        result.append("Họ tên: ").append(fullName).append("\n");
        result.append("Email: ").append(email).append("\n");
        result.append("Lớp: ").append(grade).append("\n");
        result.append("Môn tham gia: ").append(String.join(", ", selectedSports));

        tvResult.setText(result.toString());
        tvResult.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        etFullName.setText("");
        etEmail.setText("");
        rgGrade.check(R.id.rbGrade10);
        cbBongBan.setChecked(false);
        cbCauLong.setChecked(false);
        cbCoVua.setChecked(false);
        tvResult.setVisibility(View.GONE);
        etFullName.requestFocus();
    }
}
