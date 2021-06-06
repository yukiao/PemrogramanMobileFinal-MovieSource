package com.yukiao.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yukiao.movie_app.activities.DetailActivity;
import com.yukiao.movie_app.R;
import com.yukiao.movie_app.adapters.MovieAdapter;
import com.yukiao.movie_app.utils.OnItemClick;
import com.yukiao.movie_app.models.NowPlaying;
import com.yukiao.movie_app.models.NowPlayingResponse;
import com.yukiao.movie_app.network.Const;
import com.yukiao.movie_app.network.MovieApiClient;
import com.yukiao.movie_app.network.MovieApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment extends Fragment implements OnItemClick {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<NowPlaying> airingTodays;
    private ProgressBar progressBar;
    private String layoutName;

    public TrendingFragment(String layoutName){
        this.layoutName = layoutName;
    }
    public TrendingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        progressBar = view.findViewById(R.id.pb_main);

        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        loadData();
        return view;
    }

    private void loadData() {
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit().create(MovieApiInterface.class);
        Call<NowPlayingResponse> nowPlayingCall = getEndPointResource(movieApiInterface);
        nowPlayingCall.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                try{
                    if(response.isSuccessful() && response.body().getNowPlayings() != null){
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        airingTodays = response.body().getNowPlayings();
                        adapter = new MovieAdapter(airingTodays, TrendingFragment.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                Log.d("Trending", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", airingTodays.get(pos).getId());
        detailActivity.putExtra("TYPE", "Movie");
        startActivity(detailActivity);
    }

    private Call<NowPlayingResponse> getEndPointResource(MovieApiInterface movieApiInterface){
        switch (layoutName){
            case Const.NOW_PLAYING:
                return movieApiInterface.getNowPlaying(Const.API_KEY, 1);
            case Const.UPCOMING:
                return movieApiInterface.getUpcoming(Const.API_KEY,1);
            case Const.POPULAR:
                return movieApiInterface.getPopular(Const.API_KEY, 1);
            default:
                return null;
        }
    }
}