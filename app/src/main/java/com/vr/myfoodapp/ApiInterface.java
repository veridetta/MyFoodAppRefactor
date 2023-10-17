package com.vr.myfoodapp;


import com.vr.myfoodapp.model.FoodData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("CKVpcp")
    Call<FoodData> getAllData();


    // lets make our model class of json data.

}
