package com.yukiao.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.yukiao.movie_app.activities.DetailActivity;
import com.yukiao.movie_app.R;
import com.yukiao.movie_app.adapters.MovieAdapter;
import com.yukiao.movie_app.network.repository.MovieRepository;
import com.yukiao.movie_app.network.repository.callback.OnMovieCallback;
import com.yukiao.movie_app.network.repository.callback.OnSearchCallback;
import com.yukiao.movie_app.utils.OnItemClick;
import com.yukiao.movie_app.models.Movies;
import com.yukiao.movie_app.network.Const;

import java.util.List;

public class MovieFragment extends Fragment implements OnItemClick, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movies> movies;
    private String layoutName;
    private int currentPage = 1;
    private boolean isFetching;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;
    private MovieRepository repository;

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
        repository = MovieRepository.getInstance();

        recyclerView = view.findViewById(R.id.rv_tv_show);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        getRepositoryData("",currentPage);
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
                        getRepositoryData("",currentPage+1);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", movies.get(pos).getId());
        startActivity(detailActivity);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {
        inflater.inflate(R.menu.movie_fragment_toolbar,menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void getRepositoryData(String query, int page){
        isFetching = true;
        if(query.equals("")){
            switch (layoutName){
                case Const.NOW_PLAYING:
                    repository.getNowPlayingMovie(page, new OnMovieCallback() {
                        @Override
                        public void onSuccess(int page, List<Movies> movieList) {
                            onSuccessResponse(page, movieList);
                        }

                        @Override
                        public void onFailure(String message) {
                            onFailureResponse(message);
                        }
                    });
                case Const.UPCOMING:
                    repository.getUpcomingMovies(page, new OnMovieCallback() {
                        @Override
                        public void onSuccess(int page, List<Movies> movieList) {
                            onSuccessResponse(page, movieList);
                        }

                        @Override
                        public void onFailure(String message) {
                            onFailureResponse(message);
                        }
                    });
                case Const.POPULAR:
                    repository.getPopularMovies(page, new OnMovieCallback() {
                        @Override
                        public void onSuccess(int page, List<Movies> movieList) {
                            onSuccessResponse(page, movieList);
                        }

                        @Override
                        public void onFailure(String message) {
                            onFailureResponse(message);
                        }
                    });
            }
        }else{
            repository.searchMovie(query, page, new OnSearchCallback() {
                @Override
                public void onSuccess(int page, List<Movies> moviesList, String msg) {
                    onSuccessResponse(page, moviesList);
                }

                @Override
                public void onFailure(String msg) {
                    onFailureResponse(msg);
                }
            });
        }
    }

    private void onSuccessResponse(int page, List<Movies> movieList){
        if(adapter == null){
            movies = movieList;
            adapter = new MovieAdapter(movieList,MovieFragment.this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }else{
            adapter.appendList(movieList);
        }
        currentPage = page;
        isFetching = false;
        refreshLayout.setRefreshing(false);
    }

    private void onFailureResponse(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 0) {
            adapter = null;
            getRepositoryData(s,currentPage);
        }else{
            adapter = null;
            getRepositoryData("",currentPage);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        adapter = null;
        currentPage = 1;
        getRepositoryData("", currentPage);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(searchView != null){
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
    }
}