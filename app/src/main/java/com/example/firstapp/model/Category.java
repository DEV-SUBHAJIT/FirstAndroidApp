package com.example.firstapp.model;

public class Category {
    private int id;
    private String categoryName, categoryImage, categoryDesc, createdAt;
    private boolean isActive;

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }
}
