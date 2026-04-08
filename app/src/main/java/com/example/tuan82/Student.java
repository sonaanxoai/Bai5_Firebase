package com.example.tuan82;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    @NonNull
    public String username;
    public String studentName;
    public String subject;
    public float score;

    public Student(@NonNull String username, String studentName, String subject, float score) {
        this.username = username;
        this.studentName = studentName;
        this.subject = subject;
        this.score = score;
    }
}
