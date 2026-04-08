package com.example.tuan82;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts")
public class Account {
    @PrimaryKey
    @NonNull
    public String username;
    public String password;

    public Account(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
    }
}
