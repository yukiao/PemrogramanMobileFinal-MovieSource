package com.yukiao.movie_app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AiringTodayResponse {
    @SerializedName("results")
    @Expose
    private List<AiringToday> airingTodays;

    public List<AiringToday> getAiringTodays(){
        return airingTodays;
    }
}
