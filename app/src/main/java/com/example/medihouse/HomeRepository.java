package com.example.medihouse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.medihouse.model.Articles;
import com.example.medihouse.network.APICall;
import com.example.medihouse.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {

    ApiInterface apiInterface;
    MutableLiveData<ArrayList<Articles>> result = new MutableLiveData<>();
    ArrayList<Articles> articlesList = new ArrayList<>();

    public HomeRepository() {
        apiInterface = APICall.getApiCall().create(ApiInterface.class);
    }

    public LiveData<ArrayList<Articles>> getAllArticles() {
        Call<ArrayList<Articles>> call = apiInterface.getAllArticles();
        call.enqueue(new Callback<ArrayList<Articles>>() {
            @Override
            public void onResponse(Call<ArrayList<Articles>> call, Response<ArrayList<Articles>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        articlesList = response.body();
                    }
                }
                result.setValue(articlesList);
            }

            @Override
            public void onFailure(Call<ArrayList<Articles>> call, Throwable t) {
                result.setValue(articlesList);
            }
        });

        return result;
    }
}
