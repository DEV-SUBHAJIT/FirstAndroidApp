package com.example.firstapp.api_response;

import com.example.firstapp.model.Orders;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {

    @SerializedName("Status")
    private  boolean Status;

    @SerializedName("Orders")
    List<Orders>ordersList;

    public boolean isStatus() {
        return Status;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }
}
