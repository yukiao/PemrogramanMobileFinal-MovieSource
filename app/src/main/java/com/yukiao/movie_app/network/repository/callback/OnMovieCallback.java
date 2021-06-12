package com.yukiao.movie_app.network.repository.callback;

import com.yukiao.movie_app.models.Movies;

import java.util.List;

public interface OnMovieCallback {
    void onSuccess(int page, List<Movies> movieList);
    void onFailure(String message);
}
