package com.yukiao.movie_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yukiao.movie_app.models.Genre;
import com.yukiao.movie_app.models.movie.Movie;
import com.yukiao.movie_app.models.tv.Tv;
import com.yukiao.movie_app.network.Const;
import com.yukiao.movie_app.network.MovieApiClient;
import com.yukiao.movie_app.network.MovieApiInterface;
import com.yukiao.movie_app.network.TVShowApiClient;
import com.yukiao.movie_app.network.TVShowApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements ActionBarTitle {
    private TextView title, releaseYear, duration, description, ratingNumber;
    private RatingBar rating;
    private ImageView cover;
    private String id,type;
    private RecyclerView recyclerView;
    private ArrayList<String> genres;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#070d2d")));
        getSupportActionBar().setTitle("");

        progressBar = findViewById(R.id.pb_main);
        progressBar.setVisibility(View.VISIBLE);

        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setVisibility(View.GONE);

        id = getIntent().getStringExtra("ID");
        type = getIntent().getStringExtra("TYPE");
        genres = new ArrayList<>();

        title = findViewById(R.id.tv_detail_title);
        releaseYear = findViewById(R.id.tv_detail_release_year);
        duration = findViewById(R.id.tv_detail_duration);
        rating = findViewById(R.id.rb_detail);
        cover = findViewById(R.id.iv_detail_cover);
        description = findViewById(R.id.tv_detail_description);
        ratingNumber = findViewById(R.id.tv_detail_rating);

        loadData();

        Log.d("DetailActivity", "From create view");
//
//        genres = new ArrayList<>();


//
//        setLayoutContent(movie);
//
//        insertGenre(movie.getGenre());
//
//        recyclerView = findViewById(R.id.rv_genre);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        recyclerView.setAdapter(new GenreRecyclerAdapter(genres, this));

    }

    public void loadData() {
        if(type.equals("Movie")){
            MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit().create(MovieApiInterface.class);

            Call<Movie> movieDetail = movieApiInterface.getMovie(id, Const.API_KEY);
            movieDetail.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if(response.isSuccessful() && response.body() != null){
                        progressBar.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        setActivityContent(response.body());
                        Log.d("DetailActivity", "From Response");
                    }
                    else {
                        Toast.makeText(DetailActivity.this, "Error OnResponse", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.d("DetailActivity", "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(DetailActivity.this, "Failed: "+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(type.equals("TV Show")){
            TVShowApiInterface tvShowApiInterface = TVShowApiClient.getRetrofit().create(TVShowApiInterface.class);

            Call<Tv> tvDetail = tvShowApiInterface.getTVShow(id, Const.API_KEY);
            tvDetail.enqueue(new Callback<Tv>() {
                @Override
                public void onResponse(Call<Tv> call, Response<Tv> response) {
                    if(response.isSuccessful() && response.body() != null){
                        progressBar.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.VISIBLE);

                        setActivityContent(response.body());
                    }
                    else {
                        Toast.makeText(DetailActivity.this, "Error OnResponse", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Tv> call, Throwable t) {
                    Log.d("DetailActivity", "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(DetailActivity.this, "Failed: "+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//
    public void setActionBarTitle(String title){
        View view = getLayoutInflater().inflate(R.layout.action_bar,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        );

        TextView titleBar = view.findViewById(R.id.tv_ab_title);
        titleBar.setText(title);

        getSupportActionBar().setCustomView(view, params);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#070d2d")));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setActivityContent(Movie movie){
        Glide.with(DetailActivity.this)
                .load(Const.IMG_URL_300 + movie.getCover())
                .into(cover);
        title.setText(movie.getTitle());
        releaseYear.setText(movie.getReleaseDate().split("-")[0]);
        duration.setText(movie.getDuration() + "min");
        rating.setRating((float)movie.getRating()/2);
        description.setText(movie.getDescription());
        ratingNumber.setText(String.valueOf(movie.getRating()));

        setGenres(movie.getGenres());

        recyclerView = findViewById(R.id.rv_genre);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new GenreRecyclerAdapter(genres, this));
        setActionBarTitle(movie.getTitle());
    }

    private void setActivityContent(Tv tv){
        Glide.with(DetailActivity.this)
                .load(Const.IMG_URL_300 + tv.getCover()).into(cover);
        title.setText(tv.getTitle());
        releaseYear.setText(tv.getFirstRelease().split("-")[0]);
        rating.setRating((float) tv.getRating()/2);
        description.setText(tv.getDescription());
        ratingNumber.setText(String.valueOf(tv.getRating()));
        duration.setText(tv.getDuration()[0] + "min");

        setGenres(tv.getGenres());

        recyclerView = findViewById(R.id.rv_genre);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new GenreRecyclerAdapter(genres, this));
        setActionBarTitle(tv.getTitle());
    }

    private void setGenres(List<Genre> genresList){
        for(int i = 0; i< genresList.size(); i++){
            genres.add(genresList.get(i).getName());
        }
    }


}