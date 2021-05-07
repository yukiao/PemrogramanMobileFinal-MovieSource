package com.yukiao.movie_app.network;

import com.yukiao.movie_app.models.AiringTodayResponse;
import com.yukiao.movie_app.models.tv.Tv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TVShowApiInterface {
    @GET("airing_today")
    Call<AiringTodayResponse> getAiringTodays(
            @Query("api_key") String apiKey
    );

    @GET("{tv_id}")
    Call<Tv> getTVShow(
            @Path("tv_id") String id,
            @Query("api_key") String apiKey
    );
}
