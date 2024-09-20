package com.example.insightlogfe.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://52.41.36.82:8089")
                .baseUrl("https://blogappapis.onrender.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
