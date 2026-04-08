package com.example.thuchanh3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fruits")
public class Fruit {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double price;
    public int quantity;
    public String expiryDate; // Format: yyyy-MM-dd

    public Fruit(String name, double price, int quantity, String expiryDate) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
}
