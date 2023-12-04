package com.example.firstapp.api_response;

import com.example.firstapp.model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    private boolean Status;

    @SerializedName("Categories")
    List<Category> categoryList;

    public boolean isStatus() {
        return Status;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
