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
//                .baseUrl("http://192.168.84.235:8080")
                .baseUrl("http://viaduct.proxy.rlwy.net:20239")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
