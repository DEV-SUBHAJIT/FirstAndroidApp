package com.example.firstapp.api_response;

import com.example.firstapp.products.Size;
import com.example.firstapp.products.SizeDetails;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SizeResponse {


    @SerializedName("Status")
    boolean Status;

    public boolean isStatus() {
        return Status;
    }

    public List<SizeDetails> getSizeList() {
        return sizeList;
    }

    @SerializedName("SizeDetail")
    List<SizeDetails> sizeList;





}
