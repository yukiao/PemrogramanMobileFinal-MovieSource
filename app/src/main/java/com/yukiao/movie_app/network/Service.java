package com.yukiao.movie_app.network;

import com.yukiao.movie_app.models.CastResponse;
import com.yukiao.movie_app.models.MoviesResponse;
import com.yukiao.movie_app.models.movie.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCast(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcoming(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/popular")
    Call<MoviesResponse> getPopular(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );
    @GET("search/movie")
    Call<MoviesResponse> getSearchResult(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );
}
