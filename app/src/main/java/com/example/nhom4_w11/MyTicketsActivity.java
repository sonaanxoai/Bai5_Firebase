package com.example.nhom4_w11;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom4_w2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {

    private ListView lvMyTickets;
    private FirebaseFirestore db;
    private List<String> ticketsDisplayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets_w11);

        lvMyTickets = findViewById(R.id.lvMyTickets);
        db = FirebaseFirestore.getInstance();
        ticketsDisplayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ticketsDisplayList);
        lvMyTickets.setAdapter(adapter);

        fetchUserTickets();
    }

    private void fetchUserTickets() {
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) return;

        db.collection("tickets")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ticketsDisplayList.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Ticket t = doc.toObject(Ticket.class);
                            String info = "Phim: " + t.movieTitle + "\nRạp: " + t.theaterName + 
                                         "\nGiờ: " + t.showtime + " | Ghế: " + t.seat;
                            ticketsDisplayList.add(info);
                        }
                        if (ticketsDisplayList.isEmpty()) {
                            Toast.makeText(this, "Bạn chưa đặt vé nào", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
