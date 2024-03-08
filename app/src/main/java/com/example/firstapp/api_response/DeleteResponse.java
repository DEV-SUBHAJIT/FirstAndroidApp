package com.example.firstapp.api_response;

import com.example.firstapp.model.DeleteCart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteResponse {

    @SerializedName("Status")
    private boolean Status;

    String Message;

    @SerializedName("Cart")
    DeleteCart deleteCart;

    public boolean isStatus() {
        return Status;
    }

    public DeleteCart getDeleteCart() {
        return deleteCart;
    }

    public String getMessge() {
        return Message;
    }
}
