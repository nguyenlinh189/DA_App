package com.example.woody.api;

import retrofit2.Retrofit;

public class APIUtils {
    public static ApiServiceInterface getApiServiceInterface(){
        Retrofit retrofitInstance=RetrofitInstance.getRetrofitInstance();
        return retrofitInstance.create(ApiServiceInterface.class);
    }
}
