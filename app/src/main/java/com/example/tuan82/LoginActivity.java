package com.example.tuan82;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tuan8);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        db = AppDatabase.getDatabase(this);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();

            Account account = db.appDao().login(user, pass);
            if (account != null) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs82", MODE_PRIVATE);
                sharedPreferences.edit().putString("username", user).apply();

                startActivity(new Intent(this, ScoreActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
