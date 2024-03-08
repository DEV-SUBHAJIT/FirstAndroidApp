package com.example.firstapp.products;

import com.example.firstapp.api_response.ColorResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ColorDetails  {
    private int id, colorId;

    private String colorName;

    @SerializedName("productImage")
    private List<ProductImage> productImageList;

    @SerializedName("rating")
    private List<Rating> ratings;

    @SerializedName("sizeDetails")
    private SizeDetails sizeDetails;

    public int getId() {
        return id;
    }

    public int getColorId() {
        return colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public SizeDetails getSizeDetails() {
        return sizeDetails;
    }
}
