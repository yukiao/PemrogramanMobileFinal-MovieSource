package com.yukiao.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yukiao.movie_app.adapters.NowPlayingAdapter;
import com.yukiao.movie_app.adapters.OnItemClick;
import com.yukiao.movie_app.models.NowPlaying;
import com.yukiao.movie_app.models.NowPlayingResponse;
import com.yukiao.movie_app.network.Const;
import com.yukiao.movie_app.network.MovieApiClient;
import com.yukiao.movie_app.network.MovieApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemClick {
    private static final String TAG = "HomeFragment";
    private RecyclerView recyclerView;
    private NowPlayingAdapter adapter;
    private List<NowPlaying> nowPlayings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        loadData();
        return view;
    }

    public void loadData(){
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofit().create(MovieApiInterface.class);

        Call<NowPlayingResponse> nowPlayingCall = movieApiInterface.getNowPlaying(Const.API_KEY);
        nowPlayingCall.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                if(response.isSuccessful() && response.body().getNowPlayings() != null){
                    nowPlayings = response.body().getNowPlayings();
                    adapter = new NowPlayingAdapter(nowPlayings, HomeFragment.this);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", nowPlayings.get(pos).getId());
        detailActivity.putExtra("TYPE", "Movie");
        startActivity(detailActivity);
    }
}