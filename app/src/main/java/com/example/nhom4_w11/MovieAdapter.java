package com.example.nhom4_w11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.nhom4_w2.R;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private List<Movie> movies;

    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, R.layout.item_movie_w11, movies);
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie_w11, parent, false);
        }

        Movie movie = movies.get(position);

        ImageView ivPoster = convertView.findViewById(R.id.ivMoviePoster);
        TextView tvTitle = convertView.findViewById(R.id.tvMovieTitle);
        TextView tvGenre = convertView.findViewById(R.id.tvMovieGenre);
        TextView tvDuration = convertView.findViewById(R.id.tvMovieDuration);

        tvTitle.setText(movie.title);
        tvGenre.setText(movie.genre);
        tvDuration.setText(movie.duration);

        // Sử dụng ảnh ao.jpg từ drawable làm ảnh mặc định/lỗi
        Glide.with(context)
                .load(movie.imageUrl)
                .placeholder(R.drawable.ao)
                .error(R.drawable.ao)
                .into(ivPoster);

        return convertView;
    }
}
