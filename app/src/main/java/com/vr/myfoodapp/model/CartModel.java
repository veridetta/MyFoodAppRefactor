
package com.vr.myfoodapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("price")
    @Expose
    private String price;

    public CartModel(String name, String imageUrl, String rating,  String price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
