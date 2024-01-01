package com.example.firstapp.api_response;

import com.example.firstapp.model.News;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    boolean status;
    int total;
    String message;

    @SerializedName("news")
    List<News> newsList;

    public boolean isStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public String getMessage() {
        return message;
    }

    public List<News> getNewsList() {
        return newsList;
    }
}
