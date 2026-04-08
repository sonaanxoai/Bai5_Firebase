package com.example.tuan8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

public class ScoreActivity extends AppCompatActivity {

    TextView tvWelcome;
    TableLayout tableScores;
    Button btnLogout;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_tuan8);

        tvWelcome = findViewById(R.id.tvWelcome);
        tableScores = findViewById(R.id.tableScores);
        btnLogout = findViewById(R.id.btnLogout);
        dbHelper = new DBHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (username.isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            tvWelcome.setText("Welcome, " + username);
            displayScores(username);
        }

        btnLogout.setOnClickListener(v -> {
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void displayScores(String user) {
        // Keep only header
        int count = tableScores.getChildCount();
        if (count > 1) {
            tableScores.removeViews(1, count - 1);
        }

        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        Cursor cursor = dbHelper.getScores(user);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);
                row.setPadding(0, 16, 0, 16);
                row.setGravity(Gravity.CENTER_VERTICAL);

                TextView tvName = new TextView(this);
                tvName.setText(cursor.getString(0)); // COL_STU_NAME
                tvName.setLayoutParams(params);
                tvName.setPadding(8, 0, 8, 0);

                TextView tvSub = new TextView(this);
                tvSub.setText(cursor.getString(1)); // COL_SUBJECT
                tvSub.setLayoutParams(params);
                tvSub.setPadding(8, 0, 8, 0);

                TextView tvScore = new TextView(this);
                tvScore.setText(String.valueOf(cursor.getFloat(2))); // COL_SCORE
                tvScore.setLayoutParams(params);
                tvScore.setPadding(8, 0, 8, 0);

                row.addView(tvName);
                row.addView(tvSub);
                row.addView(tvScore);

                tableScores.addView(row);
            }
            cursor.close();
        }
    }
}
