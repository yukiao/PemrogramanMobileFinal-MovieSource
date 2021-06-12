package com.yukiao.movie_app.network.repository.callback;

import com.yukiao.movie_app.models.Casts;
import com.yukiao.movie_app.models.movie.Movie;

import java.util.List;

public interface OnMovieDetailCallback {
    void onSuccess(Movie movie, List<Casts> casts, String message);
    void onFailure(String message);
}
