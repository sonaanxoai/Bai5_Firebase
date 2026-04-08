package com.example.tuan82;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    TextView tvWelcome;
    TableLayout tableStudents;
    Button btnLogout;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tuan82);

        tvWelcome = findViewById(R.id.tvWelcome82);
        tableStudents = findViewById(R.id.tableStudents);
        btnLogout = findViewById(R.id.btnLogout82);
        db = AppDatabase.getDatabase(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs82", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (username.isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            tvWelcome.setText("Welcome, " + username);
            List<Student> students = db.appDao().getStudentsForUser(username);
            displayStudents(students);
        }

        btnLogout.setOnClickListener(v -> {
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void displayStudents(List<Student> students) {
        // Keep only the header row (index 0)
        int childCount = tableStudents.getChildCount();
        if (childCount > 1) {
            tableStudents.removeViews(1, childCount - 1);
        }

        // Layout params for cells to ensure they take equal width
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);

        for (Student s : students) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 16, 0, 16);
            row.setGravity(Gravity.CENTER_VERTICAL);
            
            TextView tvName = new TextView(this);
            tvName.setText(s.studentName);
            tvName.setLayoutParams(params);
            tvName.setPadding(8, 0, 8, 0);
            
            TextView tvSubject = new TextView(this);
            tvSubject.setText(s.subject);
            tvSubject.setLayoutParams(params);
            tvSubject.setPadding(8, 0, 8, 0);
            
            TextView tvScore = new TextView(this);
            tvScore.setText(String.valueOf(s.score));
            tvScore.setLayoutParams(params);
            tvScore.setPadding(8, 0, 8, 0);

            row.addView(tvName);
            row.addView(tvSubject);
            row.addView(tvScore);
            
            tableStudents.addView(row);
        }
    }
}
