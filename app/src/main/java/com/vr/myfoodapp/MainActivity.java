package com.vr.myfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vr.myfoodapp.adapter.AllMenuAdapter;
import com.vr.myfoodapp.adapter.PopularAdapter;
import com.vr.myfoodapp.adapter.RecommendedAdapter;
import com.vr.myfoodapp.model.Allmenu;
import com.vr.myfoodapp.model.FoodData;
import com.vr.myfoodapp.model.Popular;
import com.vr.myfoodapp.model.Recommended;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    RecyclerView popularRecyclerView, recommendedRecyclerView, allMenuRecyclerView;

    PopularAdapter popularAdapter;
    ImageView imageView;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> {
            //intent ke cartactivity
            Intent intent = new Intent(imageView.getContext(), CartActivity.class);
            imageView.getContext().startActivity(intent);
        });

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<FoodData> call = apiInterface.getAllData();
        Log.d("TAG", "onResponse: "+call.request().url());
        call.enqueue(new Callback<FoodData>() {
            @Override
            public void onResponse(Call<FoodData> call, Response<FoodData> response) {
                Log.d("TAG", response.code()+"");
                Log.d("TAG", response.body()+"");
                FoodData foodData = response.body();
                getPopularData(foodData.getPopular());
                getRecommendedData(foodData.getRecommended());
                getAllMenu(foodData.getAllmenu());
                // lets run it.
                // we have fetched data from server.
                // now we have to show data in app using recycler view
                // lets make recycler view adapter
                // we have setup and bind popular section
                // in a same way we add recommended and all menu items
                // we add two adapter class for allmenu and recommended items.
                // so lets do it fast.

            }

            @Override
            public void onFailure(Call<FoodData> call, Throwable t) {
                Log.e("TAG", "Request failed: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void  getPopularData(List<Popular> popularList){

        popularRecyclerView = findViewById(R.id.popular_recycler);
        popularAdapter = new PopularAdapter(this, popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

    private void  getAllMenu(List<Allmenu> allmenuList){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllMenuAdapter(this, allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }
    // today w are going to make a food app like zomato and swiggy.
    // we have 3 category in data
    // popular items, recommended items and all menu,
    // lets have have a look on json data.
    // so lets start coding.
    // lets add retrofit dependency in gradle file for network call.
    // we have setup model class and adapter class
    // now we are going to setup data in recyclerview.
    // complited all recyclerview
    // now we will setup on click listener on items.
    // tutorial complited see you in the next video.
}
