package com.example.medihouse.network;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICall {

    public static Retrofit retrofit;

    public static Retrofit getApiCall(){
        if (retrofit == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(100, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).build();
            retrofit = new Retrofit.Builder().baseUrl("https://api.spaceflightnewsapi.net/").addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }

        return retrofit;
    }
}
