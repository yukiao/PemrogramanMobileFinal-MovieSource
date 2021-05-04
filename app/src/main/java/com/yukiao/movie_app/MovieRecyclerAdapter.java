package com.yukiao.movie_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {
    private List<MovieModel> movies;
    private OnItemClickListener<MovieModel> clickListener;

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    public void setClickListener(OnItemClickListener<MovieModel> clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_component, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MovieModel movie;
        ImageView ivCover;
        TextView tvTitle;
        RatingBar rbRating;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rbRating = itemView.findViewById(R.id.rb_movie_rating);
        }

        public void onBind(MovieModel movie){
            this.movie = movie;
            ivCover.setImageResource(movie.getCover());
            tvTitle.setText(movie.getTitle());
            rbRating.setRating((float )movie.getRating()/2);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(movie);
        }
    }
}

