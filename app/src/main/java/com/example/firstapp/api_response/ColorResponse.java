package com.example.firstapp.api_response;


import com.example.firstapp.model.Color;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ColorResponse {
    public boolean isStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public int getAvailableColor() {
        return AvailableColor;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    @SerializedName("Status")
    boolean Status;
    @SerializedName("ColorDetail")
    List<Color> colorList;

    @SerializedName("Message")
    String Message;

    @SerializedName("AvailableColor")

    int AvailableColor;





}
