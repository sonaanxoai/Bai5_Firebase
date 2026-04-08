package com.example.nhom4_w11;

public class Movie {
    public String id;
    public String title;
    public String genre;
    public String duration;
    public String imageUrl;

    public Movie() {} // Required for Firebase

    public Movie(String id, String title, String genre, String duration, String imageUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }
}
