package com.yukiao.movie_app.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movies {
    private int id;
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String cover;

    @SerializedName("vote_average")
    @Expose
    private double rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
