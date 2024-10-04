package com.example.vision.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3333")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();




    public  static  API getInstance(){
        return retrofit.create(API.class);
    }
}
