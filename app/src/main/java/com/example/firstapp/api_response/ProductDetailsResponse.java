package com.example.firstapp.api_response;

import com.example.firstapp.model.Products;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsResponse {

    boolean Status;
    String Message;
    int TotalProduct;

    @SerializedName("Products")
    List<Products> productsList;

    @SerializedName("Product")
    Products products;

    public boolean isStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public int getTotalProduct() {
        return TotalProduct;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public Products getProducts() {
        return products;
    }
}
