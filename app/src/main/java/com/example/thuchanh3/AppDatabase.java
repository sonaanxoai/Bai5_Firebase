package com.example.thuchanh3;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Fruit.class, Employee.class, Transaction.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "fruit_store_db_v5")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        AppDao dao = getDatabase(context).appDao();
                                        // Dữ liệu mẫu
                                        dao.insertEmployee(new Employee("Nguyen Van A", "Nhân viên bán hàng"));
                                        dao.insertEmployee(new Employee("Tran Thi B", "Quản lý kho"));
                                        
                                        // Giả sử hôm nay là 2026-05-04
                                        // Hoa quả đã hết hạn
                                        dao.insertFruit(new Fruit("Táo Mỹ (Hết hạn)", 50000, 100, "2026-04-20"));
                                        dao.insertFruit(new Fruit("Nho Tím (Hết hạn)", 120000, 50, "2026-05-01"));
                                        
                                        // Hoa quả còn hạn (sau 2026-05-04)
                                        dao.insertFruit(new Fruit("Sầu riêng Ri6", 150000, 30, "2026-12-01"));
                                        dao.insertFruit(new Fruit("Măng cụt", 85000, 45, "2026-06-15"));
                                        dao.insertFruit(new Fruit("Xoài Cát Hòa Lộc", 60000, 80, "2027-01-10"));
                                        dao.insertFruit(new Fruit("Táo Envy", 90000, 60, "2026-08-30"));
                                        dao.insertFruit(new Fruit("Cam sành", 25000, 200, "2026-09-20"));
                                    });
                                }
                            })
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
