package com.vr.myfoodapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vr.myfoodapp.adapter.CartAdapter;
import com.vr.myfoodapp.model.CartModel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    CartAdapter cartAdapter;
    RecyclerView allMenuRecyclerView;

    ConstraintLayout lyMain;
    TextView txtTotal;
    int total = 0;
    Button btnCekOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        lyMain = findViewById(R.id.lyMain);
        txtTotal = findViewById(R.id.txtTotal);
        btnCekOut = findViewById(R.id.btnCekOut);
        List<CartModel> allmenuList = readJadwalFromFile(lyMain.getContext());
        getAllCrat(allmenuList);
        btnCekOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(lyMain.getContext(), CekOutActivity.class);
                lyMain.getContext().startActivity(intent);
            }
        });
    }

    private void getAllCrat(List<CartModel> allmenuList) {
        allMenuRecyclerView = findViewById(R.id.popular_recycler);
        cartAdapter = new CartAdapter(lyMain.getContext(), allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(lyMain.getContext(), LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        // Calculate the total and set it to txtTotal
        int total = calculateTotal(allmenuList);
        txtTotal.setText("Total: " + total + "");// Modify the format as needed

    }

    public ArrayList<CartModel> readJadwalFromFile(Context context) {
        File file = new File(context.getFilesDir(), "cart.json");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CartModel>>() {
        }.getType();

        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                ArrayList<CartModel> contacts = gson.fromJson(reader, type);
                reader.close();
                return contacts;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private int calculateTotal(List<CartModel> allmenuList) {
        int total = 0;
        for (CartModel item : allmenuList) {
            // Extract the numerical part from the price string
            String priceString = item.getPrice();
            String numericPart = priceString.replaceAll("[^0-9]", ""); // Remove non-numeric characters

            // Parse the numeric part to an integer
            int itemPrice = Integer.parseInt(numericPart);
            total += itemPrice;
        }
        return total;
    }
}