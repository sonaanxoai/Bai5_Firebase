package com.example.nhom4_w11;

public class Showtime {
    public String id;
    public String movieId;
    public String theaterId;
    public String time; // e.g., "2024-05-20 19:30"

    public Showtime() {}

    public Showtime(String id, String movieId, String theaterId, String time) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.time = time;
    }
}
