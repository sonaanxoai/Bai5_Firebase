package com.example.thuchanh3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {

    AutoCompleteTextView acFruit, acEmployee;
    EditText etPrice, etExpiry, etQuantity;
    RadioButton rbBuy, rbSell;
    Button btnSubmit;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_th3);

        acFruit = findViewById(R.id.acFruit);
        acEmployee = findViewById(R.id.acEmployee);
        etPrice = findViewById(R.id.etPrice);
        etExpiry = findViewById(R.id.etExpiry);
        etQuantity = findViewById(R.id.etQuantity);
        rbBuy = findViewById(R.id.rbBuy);
        rbSell = findViewById(R.id.rbSell);
        btnSubmit = findViewById(R.id.btnSubmit);
        db = AppDatabase.getDatabase(this);

        setupAutoCompletes();

        // Ẩn/Hiện ô nhập Hạn sử dụng khi Bán/Nhập
        rbBuy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPrice.setHint("Giá nhập (/1kg)");
                etExpiry.setVisibility(View.VISIBLE);
            }
        });
        rbSell.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPrice.setHint("Giá bán (/1kg)");
                etExpiry.setVisibility(View.GONE); // Xuất hàng không cần nhập HSD
            }
        });
        
        etPrice.setHint("Giá nhập (/1kg)");
        btnSubmit.setOnClickListener(v -> saveTransaction());
    }

    private void setupAutoCompletes() {
        List<Fruit> fruits = db.appDao().getAllFruits();
        List<String> fruitNames = new ArrayList<>();
        for (Fruit f : fruits) fruitNames.add(f.name);
        acFruit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, fruitNames));

        List<Employee> employees = db.appDao().getAllEmployees();
        List<String> employeeNames = new ArrayList<>();
        for (Employee e : employees) employeeNames.add(e.name);
        acEmployee.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, employeeNames));
    }

    private void saveTransaction() {
        String fruitName = acFruit.getText().toString().trim();
        String empName = acEmployee.getText().toString().trim();
        String qtyStr = etQuantity.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        
        if (fruitName.isEmpty() || empName.isEmpty() || qtyStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(qtyStr);
        double currentTransactionPrice = Double.parseDouble(priceStr);
        String type = rbBuy.isChecked() ? "Nhập hàng" : "Bán hàng";

        Employee employee = db.appDao().getEmployeeByName(empName);
        if (employee == null) {
            employee = new Employee(empName, "Nhân viên");
            employee.id = (int) db.appDao().insertEmployee(employee);
        }

        Fruit fruit = db.appDao().getFruitByName(fruitName);
        if (fruit == null) {
            if (rbSell.isChecked()) {
                Toast.makeText(this, "Sản phẩm chưa có trong kho để bán!", Toast.LENGTH_SHORT).show();
                return;
            }
            String expiry = etExpiry.getText().toString().trim();
            if (expiry.isEmpty()) expiry = "2026-12-31";

            fruit = new Fruit(fruitName, currentTransactionPrice, 0, expiry);
            fruit.id = (int) db.appDao().insertFruit(fruit);
        } else {
            fruit.price = currentTransactionPrice;
        }

        if (rbSell.isChecked() && fruit.quantity < quantity) {
            Toast.makeText(this, "Không đủ hàng! Hiện có: " + fruit.quantity + "kg", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rbBuy.isChecked()) {
            fruit.quantity += quantity;
            String expiry = etExpiry.getText().toString().trim();
            if (!expiry.isEmpty()) fruit.expiryDate = expiry;
        } else {
            fruit.quantity -= quantity;
        }
        db.appDao().updateFruit(fruit);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        db.appDao().insertTransaction(new Transaction(fruit.id, employee.id, type, quantity, date));

        Toast.makeText(this, "Giao dịch thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
