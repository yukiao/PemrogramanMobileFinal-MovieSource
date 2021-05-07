package com.yukiao.movie_app.models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AiringToday {
    private String id;
    @SerializedName("name")
    private String title;

    @SerializedName("poster_path")
    private String cover;

    @SerializedName("vote_average")
    private double rating;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }




}
