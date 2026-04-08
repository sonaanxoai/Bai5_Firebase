package com.example.tuan8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentDB_v2";
    public static final int DB_VERSION = 2;
    
    public static final String TABLE_ACCOUNTS = "Accounts";
    public static final String TABLE_STUDENTS = "Students";
    
    public static final String COL_USER = "username";
    public static final String COL_PASS = "password";
    
    public static final String COL_STU_NAME = "student_name";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_SCORE = "score";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Accounts table
        db.execSQL("CREATE TABLE " + TABLE_ACCOUNTS + " (" +
                COL_USER + " TEXT PRIMARY KEY, " +
                COL_PASS + " TEXT)");

        // Create Students table (one user can have multiple records)
        db.execSQL("CREATE TABLE " + TABLE_STUDENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER + " TEXT, " +
                COL_STU_NAME + " TEXT, " +
                COL_SUBJECT + " TEXT, " +
                COL_SCORE + " REAL)");

        // Seed data
        db.execSQL("INSERT INTO " + TABLE_ACCOUNTS + " VALUES ('admin', '123')");
        
        insertScore(db, "admin", "Nguyễn Văn A", "Cấu trúc dữ liệu", 8.5f);
        insertScore(db, "admin", "Nguyễn Văn A", "Lập trình Android", 9.0f);
        insertScore(db, "admin", "Nguyễn Văn A", "Mạng máy tính", 7.5f);
    }

    private void insertScore(SQLiteDatabase db, String user, String name, String sub, float score) {
        ContentValues v = new ContentValues();
        v.put(COL_USER, user);
        v.put(COL_STU_NAME, name);
        v.put(COL_SUBJECT, sub);
        v.put(COL_SCORE, score);
        db.insert(TABLE_STUDENTS, null, v);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public boolean checkLogin(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + COL_USER + "=? AND " + COL_PASS + "=?", new String[]{user, pass});
        boolean ok = c.getCount() > 0;
        c.close();
        return ok;
    }

    public Cursor getScores(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_STU_NAME + ", " + COL_SUBJECT + ", " + COL_SCORE + 
                           " FROM " + TABLE_STUDENTS + " WHERE " + COL_USER + "=?", new String[]{user});
    }
}
