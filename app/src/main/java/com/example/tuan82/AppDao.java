package com.example.tuan82;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    void insertAccount(Account account);

    @Insert
    void insertStudent(Student student);

    @Query("SELECT * FROM accounts WHERE username = :user AND password = :pass LIMIT 1")
    Account login(String user, String pass);

    @Query("SELECT * FROM students WHERE username = :user")
    List<Student> getStudentsForUser(String user);
}
