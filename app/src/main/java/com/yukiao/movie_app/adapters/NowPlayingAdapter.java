package com.yukiao.movie_app.adapters;

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
import com.yukiao.movie_app.models.NowPlaying;
import com.yukiao.movie_app.network.Const;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {
    private List<NowPlaying> nowPlayings;
    private OnItemClick onItemClick;


    public NowPlayingAdapter(List<NowPlaying> nowPlayings, OnItemClick onItemClick){
        this.nowPlayings = nowPlayings;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public NowPlayingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_component, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_200 + nowPlayings.get(position).getCover())
                .into(holder.ivCover);
        holder.tvTitle.setText(nowPlayings.get(position).getTitle());
        holder.rbMovieRating.setRating((float)nowPlayings.get(position).getRating()/2);
    }

    @Override
    public int getItemCount() {
        return nowPlayings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        ImageView ivCover;
        TextView tvTitle;
        RatingBar rbMovieRating;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rbMovieRating = itemView.findViewById(R.id.rb_movie_rating);
            this.onItemClick = onItemClick;
        }

        @Override
        public void onClick(View view) {
            onItemClick.onClick(getAdapterPosition());
        }
    }
}

