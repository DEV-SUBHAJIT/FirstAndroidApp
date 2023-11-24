package com.example.firstapp.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
