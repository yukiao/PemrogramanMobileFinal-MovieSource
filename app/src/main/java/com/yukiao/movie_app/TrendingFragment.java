package com.yukiao.movie_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yukiao.movie_app.adapters.AiringTodayAdapter;
import com.yukiao.movie_app.adapters.OnItemClick;
import com.yukiao.movie_app.models.AiringToday;
import com.yukiao.movie_app.models.AiringTodayResponse;
import com.yukiao.movie_app.network.Const;
import com.yukiao.movie_app.network.TVShowApiClient;
import com.yukiao.movie_app.network.TVShowApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingFragment extends Fragment implements OnItemClick {
    private RecyclerView recyclerView;
    private AiringTodayAdapter adapter;
    private List<AiringToday> airingTodays;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        loadData();
        return view;
    }

    private void loadData() {
        TVShowApiInterface tvShowApiInterface = TVShowApiClient.getRetrofit()
                .create(TVShowApiInterface.class);

        Call<AiringTodayResponse> airingTodayResponseCall = tvShowApiInterface.getAiringTodays(Const.API_KEY);
        airingTodayResponseCall.enqueue(new Callback<AiringTodayResponse>() {
            @Override
            public void onResponse(Call<AiringTodayResponse> call, Response<AiringTodayResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    airingTodays = response.body().getAiringTodays();
                    adapter = new AiringTodayAdapter(airingTodays, TrendingFragment.this);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AiringTodayResponse> call, Throwable t) {
                Log.d("TVShowFragment", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failed " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", airingTodays.get(pos).getId());
        detailActivity.putExtra("TYPE", "TV Show");
        startActivity(detailActivity);
    }
}