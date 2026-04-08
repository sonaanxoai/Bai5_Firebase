package com.example.tuan82;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Account.class, Student.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "school_db_v2")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        AppDao dao = getDatabase(context).appDao();
                                        dao.insertAccount(new Account("admin", "123"));
                                        
                                        // Dữ liệu mẫu với nhiều môn học
                                        dao.insertStudent(new Student("admin", "Nguyễn Văn A", "Toán", 9.0f));
                                        dao.insertStudent(new Student("admin", "Nguyễn Văn A", "Lý", 8.5f));
                                        dao.insertStudent(new Student("admin", "Nguyễn Văn A", "Hóa", 9.5f));
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
