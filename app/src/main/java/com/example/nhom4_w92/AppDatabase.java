package com.example.nhom4_w92;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Category.class, Product.class, Order.class, OrderDetail.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "shopping_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        AppDao dao = getDatabase(context).appDao();
                                        // Seed Users
                                        dao.insertUser(new User("admin", "123"));
                                        dao.insertUser(new User("khachhang", "123"));

                                        // Seed Categories
                                        dao.insertCategory(new Category("Điện thoại"));
                                        dao.insertCategory(new Category("Laptop"));
                                        dao.insertCategory(new Category("Phụ kiện"));

                                        // Seed Products
                                        dao.insertProduct(new Product("iPhone 15", 25000000, "Apple iPhone 15 128GB", 1));
                                        dao.insertProduct(new Product("Samsung S23", 20000000, "Samsung Galaxy S23 Ultra", 1));
                                        dao.insertProduct(new Product("MacBook Air M2", 30000000, "Apple MacBook Air M2 8GB/256GB", 2));
                                        dao.insertProduct(new Product("Dell XPS 13", 35000000, "Dell XPS 13 9315", 2));
                                        dao.insertProduct(new Product("AirPods Pro", 5000000, "Apple AirPods Pro Gen 2", 3));
                                    });
                                }
                            })
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
