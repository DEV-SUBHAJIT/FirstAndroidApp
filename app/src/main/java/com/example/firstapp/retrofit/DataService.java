package com.example.firstapp.retrofit;

import com.example.firstapp.api_response.CategoryResponse;
import com.example.firstapp.api_response.SubCategoryResponse;
import com.example.firstapp.model.Post;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {

    @GET("/posts")
    Call<List<Post>> getPost();


    @GET("/getCategory")
    Call<CategoryResponse> getCategories();

    @GET("/getSubCategory")
    Call<SubCategoryResponse> getSubCategory(@Query("categoryId") int categoryId);
}
