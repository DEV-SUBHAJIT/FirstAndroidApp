package com.example.firstapp.api_response;

import com.example.firstapp.model.Carts;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {

    @SerializedName("Status")
    private boolean Status;

    @SerializedName("Message")
    private String Message;

    public String getMessage() {
        return Message;
    }

    @SerializedName("TotalCartCount")

    private int TotalCartCount;

    @SerializedName("CartPrice")

    private int CartPrice;

    @SerializedName("Carts")

    List<Carts> cartsList;

    public List<Carts> getCartsList() {
        return cartsList;
    }

    public boolean isStatus() {
        return Status;
    }

    public int getTotalCartCount() {
        return TotalCartCount;
    }

    public int getCartPrice() {
        return CartPrice;
    }
}
