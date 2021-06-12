package com.yukiao.movie_app.network.repository;

import com.yukiao.movie_app.models.CastResponse;
import com.yukiao.movie_app.models.Casts;
import com.yukiao.movie_app.models.MoviesResponse;
import com.yukiao.movie_app.models.movie.Movie;
import com.yukiao.movie_app.network.Const;
import com.yukiao.movie_app.network.Service;
import com.yukiao.movie_app.network.repository.callback.OnCastCallback;
import com.yukiao.movie_app.network.repository.callback.OnMovieCallback;
import com.yukiao.movie_app.network.repository.callback.OnMovieDetailCallback;
import com.yukiao.movie_app.network.repository.callback.OnSearchCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static MovieRepository repository;
    private final Service service;

    private MovieRepository(Service service){
        this.service = service;
    }

    public static MovieRepository getInstance(){
        if(repository == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL_ORIGINAL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            repository = new MovieRepository(retrofit.create(Service.class));
        }
        return repository;
    }

    public void getNowPlayingMovie(int page, final OnMovieCallback callback){
        service.getNowPlaying(Const.API_KEY, page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body().getNowPlayings() != null){
                                    callback.onSuccess(response.body().getPage(), response.body().getNowPlayings());
                                } else {
                                    callback.onFailure("response.body().getResults() is null");
                                }
                            }else{
                                callback.onFailure("response.body() is null");
                            }
                        }else{
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }
    public void getUpcomingMovies(int page, final OnMovieCallback callback){
        service.getUpcoming(Const.API_KEY, page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body().getNowPlayings() != null){
                                    callback.onSuccess(response.body().getPage(), response.body().getNowPlayings());
                                } else {
                                    callback.onFailure("response.body().getResults() is null");
                                }
                            }else{
                                callback.onFailure("response.body() is null");
                            }
                        }else{
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void getPopularMovies(int page, final OnMovieCallback callback){
        service.getPopular(Const.API_KEY, page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body().getNowPlayings() != null){
                                    callback.onSuccess(response.body().getPage(), response.body().getNowPlayings());
                                } else {
                                    callback.onFailure("response.body().getResults() is null");
                                }
                            }else{
                                callback.onFailure("response.body() is null");
                            }
                        }else{
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void getMovieDetail(int id, final OnMovieDetailCallback callback){
        service.getMovie(id, Const.API_KEY)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if(response.isSuccessful()){
                            if(response.body() != null){
                                getCast(id, new OnCastCallback() {
                                    @Override
                                    public void onSuccess(List<Casts> castList) {
                                        callback.onSuccess(response.body(), castList, response.message());
                                    }

                                    @Override
                                    public void onFailure(String msg) {

                                    }
                                });

                            }
                            else{
                                callback.onFailure("response.body() is null");
                            }
                        }
                        else{
                            callback.onFailure(response.message() + ", Error Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });

    }

    public void searchMovie(String query, int page, final OnSearchCallback callback){
        service.getSearchResult(Const.API_KEY, query, page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(response.body().getNowPlayings() != null){
                                    callback.onSuccess(response.body().getPage(), response.body().getNowPlayings(), response.message());
                                } else {
                                    callback.onFailure("response.body().getResults() is null");
                                }
                            }else{
                                callback.onFailure("response.body() is null");
                            }
                        }else{
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }
    public void getCast(int id, final OnCastCallback callback){
        service.getCast(id,Const.API_KEY)
                .enqueue(new Callback<CastResponse>() {
                    @Override
                    public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body() != null){
                                if(response.body().getCasts() != null){
                                    callback.onSuccess(response.body().getCasts());
                                }
                                else{
                                    callback.onFailure("response.body().getCasts() is null");
                                }
                            }
                            else{
                                callback.onFailure("response.body() is null");
                            }
                        }
                        else{
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CastResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

}
