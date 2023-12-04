package com.example.firstapp.model;

public class SubCategory {
    private int id, categoryId, hsnCodeId;
    private String subCategoryName, subCategoryImage, subCategoryDesc, createdAt;
    private boolean isActive;

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getHsnCodeId() {
        return hsnCodeId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public String getSubCategoryImage() {
        return subCategoryImage;
    }

    public String getSubCategoryDesc() {
        return subCategoryDesc;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }
}
