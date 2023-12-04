package com.example.firstapp.api_response;

import com.example.firstapp.model.SubCategory;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryResponse {
    @SerializedName("Status")
    private  boolean status;

    @SerializedName("SubCategories")
    private List<SubCategory> subCategoryList;

    public boolean isStatus() {
        return status;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }
}
