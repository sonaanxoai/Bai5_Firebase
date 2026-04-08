package com.example.nhom4_w11;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private TextView tvMovieTitle;
    private Spinner spnTheaters, spnShowtimes;
    private EditText etSeat;
    private Button btnConfirm;
    private FirebaseFirestore db;
    private String movieId, movieTitle;
    private List<String> theatersList, showtimesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_w11);

        db = FirebaseFirestore.getInstance();
        movieId = getIntent().getStringExtra("movieId");
        movieTitle = getIntent().getStringExtra("movieTitle");

        tvMovieTitle = findViewById(R.id.tvBookingMovieTitle);
        spnTheaters = findViewById(R.id.spnTheaters);
        spnShowtimes = findViewById(R.id.spnShowtimes);
        etSeat = findViewById(R.id.etSeat);
        btnConfirm = findViewById(R.id.btnConfirmBooking);

        tvMovieTitle.setText(movieTitle);

        loadTheaters();
        loadShowtimes();

        btnConfirm.setOnClickListener(v -> confirmBooking());
    }

    private void loadTheaters() {
        theatersList = new ArrayList<>();
        db.collection("theaters").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    theatersList.add(doc.getString("name"));
                }
                if (theatersList.isEmpty()) theatersList.add("Rạp mặc định");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theatersList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTheaters.setAdapter(adapter);
            }
        });
    }

    private void loadShowtimes() {
        showtimesList = new ArrayList<>();
        showtimesList.add("10:00 AM");
        showtimesList.add("01:30 PM");
        showtimesList.add("04:45 PM");
        showtimesList.add("08:00 PM");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, showtimesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShowtimes.setAdapter(adapter);
    }

    private void confirmBooking() {
        String userId = FirebaseAuth.getInstance().getUid();
        String theater = spnTheaters.getSelectedItem().toString();
        String time = spnShowtimes.getSelectedItem().toString();
        String seat = etSeat.getText().toString().trim();

        if (seat.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số ghế", Toast.LENGTH_SHORT).show();
            return;
        }

        Ticket ticket = new Ticket(null, userId, movieTitle, theater, time, seat);
        db.collection("tickets").add(ticket)
                .addOnSuccessListener(documentReference -> {
                    scheduleReminder(movieTitle, time);
                    Toast.makeText(this, "Đặt vé thành công! Bạn sẽ nhận được thông báo nhắc nhở.", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Đặt vé thất bại", Toast.LENGTH_SHORT).show());
    }

    private void scheduleReminder(String title, String time) {
        // Để demo ngay lập tức, tôi sẽ hẹn giờ sau 10 giây kể từ lúc đặt vé
        // Trong thực tế, bạn sẽ parse chuỗi 'time' để lấy giờ chiếu cụ thể
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("movieTitle", title);
        intent.putExtra("time", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);

        // Hẹn giờ sau 10 giây để bạn thấy được thông báo tự động hoạt động
        long triggerTime = System.currentTimeMillis() + 10000; 
        
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }
}
