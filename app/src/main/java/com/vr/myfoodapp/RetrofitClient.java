package com.vr.myfoodapp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.jsonserve.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Membuat interceptor logging HTTP
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Ganti ke Level.NONE jika ingin mematikan logging

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client) // Set OkHttpClient dengan interceptor ke Retrofit
                    .build();
        }
        return retrofit;
    }
}
