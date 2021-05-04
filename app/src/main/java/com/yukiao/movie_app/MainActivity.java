package com.yukiao.movie_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnItemClickListener<MovieModel>, ActionBarTitle{

    private RecyclerView recyclerView;
    private ArrayList<MovieModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_tv_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menu_item_tv_home:
                setActionBarTitle("Home");
                fragment = new HomeFragment();
                break;
            case R.id.menu_item_tv_trending:
                setActionBarTitle("Trending");
                fragment = new TrendingFragment();

                break;
            case R.id.menu_item_tv_account:
                setActionBarTitle("Account");
                fragment = new AccountFragment();

                break;
        }

        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(MovieModel movieModel) {
        Intent detailActivity = new Intent(this,DetailActivity.class);
        detailActivity.putExtra("MOVIE_DETAIL", (Parcelable) movieModel);
        startActivity(detailActivity);

    }

    @Override
    public void setActionBarTitle(String title) {
        View view = getLayoutInflater().inflate(R.layout.action_bar,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        );

        TextView titleBar = view.findViewById(R.id.tv_ab_title);
        titleBar.setText(title);

        getSupportActionBar().setCustomView(view, params);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#070d2d")));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}