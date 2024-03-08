package com.example.firstapp.retrofit;

import com.example.firstapp.api_response.CartResponse;
import com.example.firstapp.api_response.CategoryResponse;
import com.example.firstapp.api_response.ColorResponse;
import com.example.firstapp.api_response.DeleteResponse;
import com.example.firstapp.api_response.NewsResponse;
import com.example.firstapp.api_response.OrderResponse;
import com.example.firstapp.api_response.ProductDetailsResponse;
import com.example.firstapp.api_response.ProductsResponse;
import com.example.firstapp.api_response.SizeResponse;
import com.example.firstapp.api_response.SubCategoryResponse;
import com.example.firstapp.api_response.UserRespons;
import com.example.firstapp.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @GET("/posts")
    Call<List<Post>> getPost();


    @GET("/getCategory")
    Call<CategoryResponse> getCategories();

    @GET("/getSubCategory")
    Call<SubCategoryResponse> getSubCategory(@Query("categoryId") int categoryId);

    @GET("/news")
    Call<NewsResponse> getNews(@Query("limit") int limit, @Query("offset") int offset);

    @GET("/product")
    Call<ProductsResponse> getProducts(@Query("subCategoryId") String subCategoryId);

    @GET("/product")
    Call<ProductDetailsResponse> getProductsDetail(@Query("id") int id);

    @GET("/product")
    Call<ProductDetailsResponse> getProductsByColorId(@Query("id") int id, @Query("colorId") int colorId);

    @GET("/getAvailableSize")
    Call<SizeResponse> getSize(@Query("productId") int productId, @Query("colorDetailId") int colorDetailId);

    @GET("/colorDetails")
    Call<ColorResponse> getColor(@Query("productId") int productId);

    @GET("/user")
    Call<UserRespons> getUser(@Query("mobileNumber") String mobileNumber);

    @GET("/cart")
    Call<CartResponse> getCart(@Query("userId") int userId);

    @FormUrlEncoded
    @POST("/cart")
    Call<CartResponse> addToCart(@Field("userId") int userId, @Field("productId") int productId, @Field("totalItem") int totalItem, @Field("colorDetailId") int colorDetailId, @Field("sizeDetailId") int sizeDetailId);

    @GET("delete/cart")
    Call<DeleteResponse> removeCarts(@Query("id") int id,@Query("userId") int userId,@Query("decrease") int decrease);

    @GET("/order")
    Call<OrderResponse> order(@Query("userId") int userId);

}
