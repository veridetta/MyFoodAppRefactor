package com.vr.myfoodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vr.myfoodapp.FoodDetails;
import com.vr.myfoodapp.R;
import com.vr.myfoodapp.model.Popular;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private Context context;
    private List<Popular> popularList;

    public PopularAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_recycler_items, parent, false);
         // here we need to create a layout for recyclerview cell items.


        return new PopularViewHolder(view);
    }
    public String addNewLineToNominal(String nominal) {
        // Memasukkan garis baru setelah 2 karakter pertama
        int splitIndex = 2;

        // Memisahkan string menjadi dua bagian
        String firstPart = nominal.substring(0, splitIndex);
        String secondPart = nominal.substring(splitIndex);

        // Menggabungkan keduanya dengan karakter garis baru di antaranya
        return firstPart + "\n" + secondPart;
    }


    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.popularName.setText(popularList.get(position).getName());

        // for image we add Glide library dependency for image fetching from server

        Glide.with(context).load(popularList.get(position).getImageUrl()).into(holder.popularImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", popularList.get(position).getName());
                i.putExtra("price", addNewLineToNominal(popularList.get(position).getPrice()));
                i.putExtra("rating", popularList.get(position).getRating());
                i.putExtra("image", popularList.get(position).getImageUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public  static class PopularViewHolder extends RecyclerView.ViewHolder{

        ImageView popularImage;
        TextView popularName;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            popularName = itemView.findViewById(R.id.all_menu_name);
            popularImage = itemView.findViewById(R.id.all_menu_image);

        }
    }
}