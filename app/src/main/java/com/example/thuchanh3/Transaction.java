package com.example.thuchanh3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int fruitId;
    public int employeeId;
    public String type; // "Buy" (Nhap hang) or "Sell" (Ban hang)
    public int quantity;
    public String date;

    public Transaction(int fruitId, int employeeId, String type, int quantity, String date) {
        this.fruitId = fruitId;
        this.employeeId = employeeId;
        this.type = type;
        this.quantity = quantity;
        this.date = date;
    }
}
