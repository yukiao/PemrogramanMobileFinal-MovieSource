package com.yukiao.movie_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yukiao.movie_app.adapters.CastAdapter;
import com.yukiao.movie_app.db.AppDatabase;
import com.yukiao.movie_app.db.entities.Favorite;
import com.yukiao.movie_app.models.Casts;
import com.yukiao.movie_app.network.repository.MovieRepository;
import com.yukiao.movie_app.network.repository.callback.OnMovieDetailCallback;
import com.yukiao.movie_app.utils.ActionBarTitle;
import com.yukiao.movie_app.adapters.GenreRecyclerAdapter;
import com.yukiao.movie_app.R;
import com.yukiao.movie_app.models.Genre;
import com.yukiao.movie_app.models.movie.Movie;
import com.yukiao.movie_app.network.Const;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements ActionBarTitle {
    private TextView title, releaseYear, duration, description, ratingNumber;
    private RatingBar rating;
    private ImageView cover, posterDetail;
    private int id;
    private RecyclerView recyclerView, recyclerViewCast;
    private ArrayList<String> genres;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;
    private String favoriteTitle, favoriteImgUrl = "";
    private MovieRepository repository;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#070d2d")));
        getSupportActionBar().setTitle("");

        database = AppDatabase.getInstance(getApplicationContext());

        repository = MovieRepository.getInstance();

        progressBar = findViewById(R.id.pb_main);
        progressBar.setVisibility(View.VISIBLE);

        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setVisibility(View.GONE);

        id = getIntent().getIntExtra("ID",0);

        genres = new ArrayList<>();


        title = findViewById(R.id.tv_detail_title);
        releaseYear = findViewById(R.id.tv_detail_release_year);
        duration = findViewById(R.id.tv_detail_duration);
        rating = findViewById(R.id.rb_detail);
        cover = findViewById(R.id.iv_detail_cover);
        posterDetail = findViewById(R.id.iv_poster_detail);
        description = findViewById(R.id.tv_detail_description);
        ratingNumber = findViewById(R.id.tv_detail_rating);

        getRepositoryData();
    }

    private void getRepositoryData(){
        repository.getMovieDetail(id, new OnMovieDetailCallback() {
            @Override
            public void onSuccess(Movie movie, List<Casts> casts, String message) {
                progressBar.setVisibility(View.GONE);
                constraintLayout.setVisibility(View.VISIBLE);
                setActivityContent(movie, casts);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(DetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_like:
                int movieId = id;
                boolean exists = database.favoriteDao().isExists(movieId);

                if(exists){
                    Favorite favorite = database.favoriteDao().findById(movieId);
                    database.favoriteDao().delete(favorite).subscribe(() -> {
                        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_border));
                        Toast.makeText(this, "Removed From favorite", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
                    });

                }else{
                    Favorite favorite = new Favorite(movieId,favoriteTitle, favoriteImgUrl);
                    database.favoriteDao().addFavorite(favorite).subscribe(() -> {
                        item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite));
                        item.getIcon().setColorFilter(getResources().getColor(R.color.active_tab_color), PorterDuff.Mode.SRC_ATOP);

                        Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this, "Failed To Add", Toast.LENGTH_SHORT).show();
                    });
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setActionBarTitle(String title){
        View view = getLayoutInflater().inflate(R.layout.action_bar,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        );

        TextView titleBar = view.findViewById(R.id.tv_ab_title);
        titleBar.setText(title);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setCustomView(view, params);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#070d2d")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left);

    }


    private void setActivityContent(Movie movie, List<Casts> casts){

        favoriteTitle = movie.getTitle();
        favoriteImgUrl = movie.getCover();

        Glide.with(DetailActivity.this)
                .load(Const.IMG_URL_200 + movie.getBackdrop())
                .into(cover);

        Glide.with(DetailActivity.this)
                .load(Const.IMG_URL_200 + movie.getCover())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .into(posterDetail);
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

        recyclerViewCast = findViewById(R.id.rv_cast);
        recyclerViewCast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewCast.setAdapter(new CastAdapter(casts));

    }

    private void setGenres(List<Genre> genresList){
        for(int i = 0; i< genresList.size(); i++){
            genres.add(genresList.get(i).getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_detail_toolbar, menu);

        int movieId = id;
        boolean exists = database.favoriteDao().isExists(movieId);

        if(!exists){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_border));
        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite));
            menu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.active_tab_color), PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }
}