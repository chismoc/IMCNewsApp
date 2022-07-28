package com.example.android.imcnewsapp.rest;

import com.example.android.imcnewsapp.model.HomepageModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    // Retrofit Interface
    // JSON --> GSON Library --> Java Object

    @GET("homepage_api")
    Call<Object>getHomepageApi(@QueryMap Map<String, String> params);


}
