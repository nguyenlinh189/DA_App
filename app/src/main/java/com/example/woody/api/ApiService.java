package com.example.woody.api;

import com.example.woody.dto.RequestPredict;
import com.example.woody.dto.ResponsePredict;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService= new Retrofit.Builder()
            .baseUrl("http://ptitsure.tk:5001/").addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @POST("predict")
    Call<ResponsePredict> checkWoodImage(@Body RequestPredict image);
}
