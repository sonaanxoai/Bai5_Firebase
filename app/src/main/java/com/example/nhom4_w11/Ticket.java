package com.example.nhom4_w11;

public class Ticket {
    public String id;
    public String userId;
    public String movieTitle;
    public String theaterName;
    public String showtime;
    public String seat;

    public Ticket() {}

    public Ticket(String id, String userId, String movieTitle, String theaterName, String showtime, String seat) {
        this.id = id;
        this.userId = userId;
        this.movieTitle = movieTitle;
        this.theaterName = theaterName;
        this.showtime = showtime;
        this.seat = seat;
    }
}
