package com.yukiao.movie_app.network.repository.callback;
import com.yukiao.movie_app.models.Movies;
import java.util.List;

public interface OnSearchCallback {
    void onSuccess(int page,List<Movies> moviesList, String msg);
    void onFailure(String msg);
}
