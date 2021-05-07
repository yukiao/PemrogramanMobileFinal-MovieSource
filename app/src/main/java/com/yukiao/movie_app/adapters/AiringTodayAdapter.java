package com.yukiao.movie_app.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yukiao.movie_app.R;
import com.yukiao.movie_app.adapters.OnItemClick;
import com.yukiao.movie_app.models.AiringToday;
import com.yukiao.movie_app.network.Const;

import java.util.List;

public class AiringTodayAdapter extends RecyclerView.Adapter<AiringTodayAdapter.ViewHolder> {
    private List<AiringToday> airingTodays;
    private OnItemClick onItemClick;

    public AiringTodayAdapter(List<AiringToday> airingTodays, OnItemClick onItemClick){
        this.airingTodays = airingTodays;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public AiringTodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_tv_show, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull AiringTodayAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_200 + airingTodays.get(position).getCover())
                .into(holder.ivCover);
        Log.d("AiringTodayAdapter",airingTodays.get(position).getTitle());
        holder.tvTitle.setText(airingTodays.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return airingTodays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        ImageView ivCover;
        TextView tvTitle;
        RatingBar rb;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivCover = itemView.findViewById(R.id.iv_show_cover);
            tvTitle = itemView.findViewById(R.id.show_title);
            this.onItemClick = onItemClick;
        }

        @Override
        public void onClick(View view) {
            onItemClick.onClick(getAdapterPosition());
        }
    }
}
