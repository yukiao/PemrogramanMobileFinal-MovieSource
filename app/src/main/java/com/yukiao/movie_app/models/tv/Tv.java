package com.yukiao.movie_app.models.tv;

import com.google.gson.annotations.SerializedName;
import com.yukiao.movie_app.models.Genre;

import java.util.List;

public class Tv {
    private String id;

    @SerializedName("name")
    private String title;

    @SerializedName("overview")
    private String description;

    private List<Genre> genres;

    @SerializedName("episode_run_time")
    private int [] duration;

    public int[] getDuration() {
        return duration;
    }

    public void setDuration(int[] duration) {
        this.duration = duration;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFirstRelease() {
        return firstRelease;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setFirstRelease(String firstRelease) {
        this.firstRelease = firstRelease;
    }

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("poster_path")
    private String cover;

    @SerializedName("first_air_date")
    private String firstRelease;

}
