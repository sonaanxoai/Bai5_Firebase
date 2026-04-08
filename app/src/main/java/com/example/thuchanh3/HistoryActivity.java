package com.example.thuchanh3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView lvHistory;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_th3);

        lvHistory = findViewById(R.id.lvHistory);
        db = AppDatabase.getDatabase(this);

        loadHistory();
    }

    private void loadHistory() {
        List<Transaction> transactions = db.appDao().getAllTransactions();
        List<String> displayList = new ArrayList<>();

        for (Transaction t : transactions) {
            Fruit f = db.appDao().getAllFruits().stream().filter(fr -> fr.id == t.fruitId).findFirst().orElse(null);
            Employee e = db.appDao().getAllEmployees().stream().filter(em -> em.id == t.employeeId).findFirst().orElse(null);
            
            String fruitName = (f != null) ? f.name : "Sản phẩm ẩn";
            String empName = (e != null) ? e.name : "Nhân viên ẩn";
            
            displayList.add(t.date + "\n" + t.type + ": " + fruitName + " (SL: " + t.quantity + ")\nBởi: " + empName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvHistory.setAdapter(adapter);
    }
}
