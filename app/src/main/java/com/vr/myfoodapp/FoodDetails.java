package com.vr.myfoodapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vr.myfoodapp.model.CartModel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class FoodDetails extends AppCompatActivity {

    // now we will get data from intent and set to UI

    ImageView imageView,imageView4;
    TextView itemName, itemPrice, itemRating;
    RatingBar ratingBar;
    Button button;

    String name, price, rating, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        //getintent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);
        imageView4 = findViewById(R.id.imageView4);
        button = findViewById(R.id.button);

        Glide.with(this).load(imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText(price);
        itemRating.setText(rating);
        ratingBar.setRating(Float.parseFloat(rating));
        imageView4.setOnClickListener(view -> {
            //intent ke cartactivity
            Intent intent1 = new Intent(imageView4.getContext(), CartActivity.class);
            imageView4.getContext().startActivity(intent1);
        });
        button.setOnClickListener(view -> {
            //intent ke cartactivity
            String uid = UUID.randomUUID().toString();
            CartModel cart = new CartModel(name, imageUrl, rating, price);
            ArrayList<CartModel> contacts = readJadwalFromFile(imageView.getContext());
            contacts.add(cart);
            // Simpan ulang data kontak ke file JSON
            writeJadwalToFile(imageView.getContext(), contacts);
            // Sembunyikan dialog progress
            // Tampilkan Snackbar berhasil menyimpan
            Snackbar.make(imageView4, "Berhasil menambahkan data", Snackbar.LENGTH_LONG).show();
        });
    }
    public ArrayList<CartModel> readJadwalFromFile(Context context) {
        File file = new File(context.getFilesDir(), "cart.json");
        Gson gson = new Gson();
        Log.d("TAG", "readJadwalFromFile: "+file.getAbsolutePath());
        Type type = new TypeToken<ArrayList<CartModel>>(){}.getType();
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                ArrayList<CartModel> contacts = gson.fromJson(reader, type);
                reader.close();
                return contacts;
            } catch (IOException e) {
                Log.e("TAG", "readJadwalFromFile: ", e);
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public void writeJadwalToFile(Context context, ArrayList<CartModel> contacts) {
        File file = new File(context.getFilesDir(), "cart.json");
        Log.d("TAG", "writeJadwalToFile: "+file.getAbsolutePath());
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(contacts, writer);
            writer.close();
        } catch (IOException e) {
            Log.e("TAG", "writeJadwalToFile: ", e);
            e.printStackTrace();
        }
    }

}
