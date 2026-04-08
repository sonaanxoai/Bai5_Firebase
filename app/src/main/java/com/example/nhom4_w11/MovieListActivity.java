package com.example.nhom4_w11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nhom4_w2.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private ListView lvMovies;
    private FirebaseFirestore db;
    private List<Movie> moviesList;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_w11);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        db = FirebaseFirestore.getInstance();
        lvMovies = findViewById(R.id.lvMovies);

        moviesList = new ArrayList<>();
        adapter = new MovieAdapter(this, moviesList);
        lvMovies.setAdapter(adapter);

        fetchMovies();

        lvMovies.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MovieListActivity.this, BookingActivity.class);
            intent.putExtra("movieId", moviesList.get(position).id);
            intent.putExtra("movieTitle", moviesList.get(position).title);
            startActivity(intent);
        });

        findViewById(R.id.btnViewMyTickets).setOnClickListener(v -> {
            startActivity(new Intent(MovieListActivity.this, MyTicketsActivity.class));
        });

        FirebaseMessaging.getInstance().subscribeToTopic("all_users");
    }

    private void fetchMovies() {
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        moviesList.clear();
                        boolean hasImages = false;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movie.id = document.getId();
                            moviesList.add(movie);
                            if (movie.imageUrl != null && !movie.imageUrl.isEmpty()) {
                                hasImages = true;
                            }
                        }
                        
                        // Nếu không có ảnh hoặc danh sách trống, tiến hành nạp lại dữ liệu chuẩn
                        if (moviesList.isEmpty() || !hasImages) {
                            seedData();
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(this, "Lỗi kết nối Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void seedData() {
        // Danh sách phim mới kèm URL ảnh trực tiếp từ mạng
        Movie m1 = new Movie("m1", "Avengers: Endgame", "Hành động", "181 phút", "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg");
        Movie m2 = new Movie("m2", "Interstellar", "Khoa học viễn tưởng", "169 phút", "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg");
        Movie m3 = new Movie("m3", "Inception", "Hành động / Viễn tưởng", "148 phút", "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg");
        Movie m4 = new Movie("m4", "The Dark Knight", "Hành động / Kịch tính", "152 phút", "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg");

        // Sử dụng .document(id).set() để ghi đè (tránh trùng lặp)
        db.collection("movies").document(m1.id).set(m1);
        db.collection("movies").document(m2.id).set(m2);
        db.collection("movies").document(m3.id).set(m3);
        db.collection("movies").document(m4.id).set(m4).addOnSuccessListener(aVoid -> fetchMovies());
        
        // Cập nhật rạp
        db.collection("theaters").document("t1").set(new Theater("t1", "CGV Vincom", "Hà Nội"));
        db.collection("theaters").document("t2").set(new Theater("t2", "Lotte Cinema", "TP.HCM"));
    }
}
