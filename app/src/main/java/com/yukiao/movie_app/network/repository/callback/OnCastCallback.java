package com.yukiao.movie_app.network.repository.callback;

import com.yukiao.movie_app.models.Casts;

import java.util.List;

public interface OnCastCallback {
    void onSuccess(List<Casts> castList);
    void onFailure(String msg);
}
