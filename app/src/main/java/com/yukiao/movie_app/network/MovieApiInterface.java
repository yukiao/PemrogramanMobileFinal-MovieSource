package com.yukiao.movie_app.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.yukiao.movie_app.models.NowPlayingResponse;
import com.yukiao.movie_app.models.movie.Movie;


public interface MovieApiInterface {
    @GET("now_playing")
    Call<NowPlayingResponse> getNowPlaying(
            @Query("api_key") String apiKey
    );

    @GET("{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") String id,
            @Query("api_key") String apiKey
    );

}
