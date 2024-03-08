package com.example.firstapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.firstapp.products.ColorDetails;
import com.google.gson.annotations.SerializedName;

public class Color implements Parcelable {

    private  int id;

    @SerializedName("colorName")
    private String colorName;

    public Color(int id, String colorName, String productImageUrl) {
        this.id = id;
        this.colorName = colorName;
        this.productImageUrl = productImageUrl;
    }

    protected Color(Parcel in) {
        id = in.readInt();
        colorName = in.readString();
        productImageUrl = in.readString();
    }

    public static final Creator<Color> CREATOR = new Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    @SerializedName("productImageUrl")

  private String productImageUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(colorName);
        dest.writeString(productImageUrl);
    }
}
