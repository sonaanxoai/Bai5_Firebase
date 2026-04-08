package com.example.nhom4_w92;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int orderId;
    public int userId;
    public String orderDate;
    public String status; // e.g., "Pending", "Paid"

    public Order(int userId, String orderDate, String status) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
    }
}
