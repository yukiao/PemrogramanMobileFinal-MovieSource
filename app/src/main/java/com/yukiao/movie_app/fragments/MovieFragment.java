package com.yukiao.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.yukiao.movie_app.activities.DetailActivity;
import com.yukiao.movie_app.R;
import com.yukiao.movie_app.adapters.MovieAdapter;
import com.yukiao.movie_app.network.SearchApiClient;
import com.yukiao.movie_app.network.SearchApiInterface;
import com.yukiao.movie_app.utils.OnItemClick;
import com.yukiao.movie_app.models.Movies;
import com.yukiao.movie_app.models.MoviesResponse;
import com.yukiao.movie_app.network.Const;
import com.yukiao.movie_app.network.MovieApiClient;
import com.yukiao.movie_app.network.MovieApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment implements OnItemClick, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movies> movies;
//    private ProgressBar progressBar;
    private String layoutName;
    private int currentPage = 1;
    private boolean isFetching;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;

    public MovieFragment(String layoutName){
        this.layoutName = layoutName;
    }
    public MovieFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        refreshLayout = view.findViewById(R.id.srl_movie);
        refreshLayout.setOnRefreshListener(this);
//        progressBar = view.findViewById(R.id.pb_main);

        recyclerView = view.findViewById(R.id.rv_tv_show);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        loadData(currentPage);
        onScrollListener();

        return view;
    }

    private void onScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int visibleItem = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if(firstVisibleItem + visibleItem >=totalItem /2){
                    if(!isFetching){
                        loadData(currentPage + 1);
                    }
                }
            }
        });
    }

    private void loadData(int page) {
        isFetching = true;
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit().create(MovieApiInterface.class);
        Call<MoviesResponse> nowPlayingCall = getEndPointResource(movieApiInterface, page);
        nowPlayingCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                try{
                    if(response.isSuccessful() && response.body().getNowPlayings() != null){
//                        progressBar.setVisibility(View.GONE);
                        if(adapter == null){
                            recyclerView.setVisibility(View.VISIBLE);
                            movies = response.body().getNowPlayings();
                            adapter = new MovieAdapter(movies, MovieFragment.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        } else{
                            adapter.appendList(response.body().getNowPlayings());
                        }
                        currentPage = page;
                        isFetching = false;
                        refreshLayout.setRefreshing(false);
                    }
                    else{
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Trending", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", movies.get(pos).getId());
        startActivity(detailActivity);
    }

    private Call<MoviesResponse> getEndPointResource(MovieApiInterface movieApiInterface, int page){
        switch (layoutName){
            case Const.NOW_PLAYING:
                return movieApiInterface.getNowPlaying(Const.API_KEY, page);
            case Const.UPCOMING:
                return movieApiInterface.getUpcoming(Const.API_KEY,page);
            case Const.POPULAR:
                return movieApiInterface.getPopular(Const.API_KEY, page);
            default:
                return null;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {
        inflater.inflate(R.menu.movie_fragment_toolbar,menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void searchMovie(String keyword, int page){
        isFetching = true;
        SearchApiInterface searchApiInterface = SearchApiClient.getRetrofit().create(SearchApiInterface.class);
        Call<MoviesResponse> searchMovieCall = searchApiInterface.getSearchResult(Const.API_KEY, keyword, page);
        searchMovieCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                try{
                    if(response.isSuccessful() && response.body().getNowPlayings() != null){
                        if(adapter == null){
                            movies = response.body().getNowPlayings();
                            adapter = new MovieAdapter(movies, MovieFragment.this);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }else{
                            adapter.appendList(response.body().getNowPlayings());
                        }
                        currentPage = page;
                        isFetching = false;
                        refreshLayout.setRefreshing(false);
                    }
                    else{
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Trending", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 0) {
            adapter = null;
            searchMovie(s,currentPage);
        }else{
            adapter = null;
            loadData(currentPage);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        adapter = null;
        loadData(currentPage);
    }
}