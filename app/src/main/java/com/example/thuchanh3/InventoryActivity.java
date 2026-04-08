package com.example.thuchanh3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InventoryActivity extends AppCompatActivity {

    ListView lvInventory;
    AppDatabase db;
    
    // Cập nhật: Ngày giả định "Hôm nay" là 05/04/2026 (theo yêu cầu của bạn)
    private final String TODAY_STR = "2026-04-05";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_th3);

        lvInventory = findViewById(R.id.lvInventory);
        db = AppDatabase.getDatabase(this);

        loadInventory();
    }

    private void loadInventory() {
        List<Fruit> fruits = db.appDao().getAllFruits();

        ArrayAdapter<Fruit> adapter = new ArrayAdapter<Fruit>(this, android.R.layout.simple_list_item_2, android.R.id.text1, fruits) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                Fruit f = getItem(position);
                text1.setText(f.name + " - Tồn kho: " + f.quantity + " kg");
                text2.setText("HSD: " + f.expiryDate + " - Giá: " + String.format("%,.0f", f.price) + " đ/kg");

                try {
                    Date expiryDate = sdf.parse(f.expiryDate);
                    Date todayDate = sdf.parse(TODAY_STR);

                    if (expiryDate != null && expiryDate.before(todayDate)) {
                        // Đã hết hạn (HSD nằm trước ngày 05/04/2026)
                        text1.setTextColor(Color.RED);
                        text2.setTextColor(Color.RED);
                        if (!text2.getText().toString().contains("ĐÃ HẾT HẠN")) {
                            text2.setText(text2.getText() + " (ĐÃ HẾT HẠN)");
                        }
                    } else {
                        // Còn hạn
                        text1.setTextColor(Color.BLACK);
                        text2.setTextColor(Color.parseColor("#388E3C")); // Màu xanh lá
                    }
                } catch (ParseException e) {
                    text1.setTextColor(Color.BLACK);
                    text2.setTextColor(Color.GRAY);
                }
                return view;
            }
        };
        lvInventory.setAdapter(adapter);
    }
}
