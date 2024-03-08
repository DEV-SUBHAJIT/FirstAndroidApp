package com.example.firstapp.products;

import com.google.gson.annotations.SerializedName;

public class SizeDetails {

    private int id, productMRP, productSellPrice, colorDetailId;
    private String size;

    @SerializedName("sku")
    private Sku sku;

    public Sku getSku() {
        return sku;
    }

    public int getId() {
        return id;
    }

    public int getProductMRP() {
        return productMRP;
    }

    public int getProductSellPrice() {
        return productSellPrice;
    }

    public int getColorDetailId() {
        return colorDetailId;
    }

    public String getSize() {
        return size;
    }


}
