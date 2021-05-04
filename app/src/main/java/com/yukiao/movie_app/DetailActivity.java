package com.yukiao.movie_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements ActionBarTitle {
    private MovieModel movie;
    private ArrayList<String> genres;
    private RecyclerView recyclerView;
    private TextView title, releaseYear, duration, description, writers, ratingNumber;
    private RatingBar rating;
    private ImageView cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movie = getIntent().getExtras().getParcelable("MOVIE_DETAIL");

        title = findViewById(R.id.tv_detail_title);
        releaseYear = findViewById(R.id.tv_detail_release_year);
        duration = findViewById(R.id.tv_detail_duration);
        rating = findViewById(R.id.rb_detail);
        cover = findViewById(R.id.iv_detail_cover);
        description = findViewById(R.id.tv_detail_description);
        writers = findViewById(R.id.tv_detail_writers);
        ratingNumber = findViewById(R.id.tv_detail_rating);

        genres = new ArrayList<>();

        setActionBarTitle(movie.getTitle());

        setLayoutContent(movie);

        insertGenre(movie.getGenre());

        recyclerView = findViewById(R.id.rv_genre);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new GenreRecyclerAdapter(genres, this));

    }

    /**
     *
     * @param plainGenreText a plain text of genres separated by ", "
     */
    private void insertGenre(String plainGenreText){
        String [] arrayOfGenre = plainGenreText.split(", ");
        for(int i=0; i<arrayOfGenre.length; i++){
            genres.add(arrayOfGenre[i]);
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

    public void setActionBarTitle(String title){
//        ActionBar action = getSupportActionBar();
//        action.setTitle(title);
//        action.setDisplayHomeAsUpEnabled(true);
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

    private void setLayoutContent(MovieModel movie){
        title.setText(movie.getTitle());
        releaseYear.setText(movie.getReleaseDate());
        duration.setText(movie.getDuration());
        rating.setRating((float) movie.getRating()/2);

        cover.setImageResource(movie.getCover());
        final Matrix matrix = cover.getImageMatrix();
        final float imageWidth = cover.getDrawable().getIntrinsicWidth();
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final float scaleRatio = screenWidth/imageWidth;
        matrix.postScale(scaleRatio, scaleRatio);
        cover.setImageMatrix(matrix);

        description.setText(movie.getDetail());
        writers.setText("Writers: "+movie.getAuthor());
        ratingNumber.setText(String.valueOf(movie.getRating()));
    }
}