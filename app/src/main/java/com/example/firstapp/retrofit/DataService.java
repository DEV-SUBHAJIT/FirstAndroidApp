package com.example.firstapp.retrofit;

import com.example.firstapp.model.Post;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("/posts")
    Call<List<Post>> getPost();

}
