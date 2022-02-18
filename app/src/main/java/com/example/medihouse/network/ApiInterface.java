package com.example.medihouse.network;

import com.example.medihouse.model.Articles;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("v3/articles")
    Call<ArrayList<Articles>> getAllArticles();
}
