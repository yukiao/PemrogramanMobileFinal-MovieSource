package com.yukiao.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yukiao.movie_app.activities.DetailActivity;
import com.yukiao.movie_app.R;

import com.yukiao.movie_app.adapters.FavoriteAdapter;

import com.yukiao.movie_app.utils.OnItemClick;
import com.yukiao.movie_app.db.AppDatabase;
import com.yukiao.movie_app.db.entities.Favorite;

import java.util.List;

public class AccountFragment extends Fragment implements OnItemClick {
    private RecyclerView recyclerView;
    private AppDatabase database;
    private List<Favorite> favoriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        database = AppDatabase.getInstance(getActivity().getApplicationContext());

        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        loadData();
        return view;
    }

    private void loadData() {
        database.favoriteDao().getAll().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
                favoriteList = favorites;
                recyclerView.setAdapter(new FavoriteAdapter(favorites, AccountFragment.this));
            }
        });
    }


    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", String.valueOf(favoriteList.get(pos).getId()));
        detailActivity.putExtra("TYPE", "Movie");
        startActivity(detailActivity);
    }

}