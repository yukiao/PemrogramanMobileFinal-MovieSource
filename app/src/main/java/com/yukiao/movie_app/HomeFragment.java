package com.yukiao.movie_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnItemClickListener<MovieModel> {


    private RecyclerView recyclerView;
    private ArrayList<MovieModel> list;
    private MovieRecyclerAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        list = new ArrayList<>();
        list.addAll(MovieData.getMoviesData());

        adapter = new MovieRecyclerAdapter();
        adapter.setClickListener(this);
        adapter.setMovies(list);


        recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(MovieModel movieModel) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("MOVIE_DETAIL",  movieModel);
        startActivity(detailActivity);
    }
}