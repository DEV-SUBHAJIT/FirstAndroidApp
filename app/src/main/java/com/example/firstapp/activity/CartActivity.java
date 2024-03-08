package com.example.firstapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.adapter.CartAdepter;
import com.example.firstapp.api_response.CartResponse;
import com.example.firstapp.api_response.DeleteResponse;
import com.example.firstapp.databinding.ActivityCartBinding;
import com.example.firstapp.event.CartListener;
import com.example.firstapp.model.Carts;
import com.example.firstapp.model.DeleteCart;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartListener {

    int userId;
    Context context;
    List<Carts> cartsList;

    List<DeleteCart>deleteList;


    ActivityCartBinding cartBinding;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartBinding = ActivityCartBinding.inflate(LayoutInflater.from(CartActivity.this));
        setContentView(cartBinding.getRoot());
        init();
        getCartList();
    }

    private void init() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
        context = CartActivity.this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait");
    }

    private void getCartList() {
        progressDialog.show();
        DataService service = RetrofitClient.getDataService();
        Call<CartResponse> call = service.getCart(userId);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.isStatus()) {
                        cartsList = cartResponse.getCartsList();
                        cartBinding.rvCart.setHasFixedSize(true);
                        cartBinding.rvCart.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        cartBinding.rvCart.setAdapter(new CartAdepter(cartsList,context, CartActivity.this));

                    }
                }else
                    Toast.makeText(context,"Cart Data Response Failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context,"Cart Data Not Found",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemClick(boolean isIncrement, Carts mCart) {

        Carts cart = mCart;

        int totalItem = 1;

        int colorDetailId = cart.getColorDetailId();

        int userId = cart.getUserId();

        int productId = cart.getUserId();



        int sizeDetailId = cart.getSizeDetailId();


        DataService service = RetrofitClient.getDataService();
        if (isIncrement) {
            //Add cart
            progressDialog.show();
            Call<CartResponse> call = service.addToCart(userId,productId,totalItem,colorDetailId,sizeDetailId);
            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        CartResponse cartResponse = response.body();
                        if (cartResponse.isStatus()) {
                            if (context != null)
                                Toast.makeText(CartActivity.this, ""+cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            getCartList();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CartResponse> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    if (context != null) {
                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            progressDialog.show();
            //Remove cart
            Call<DeleteResponse> call = service.removeCarts(cart.getId(), userId, 1);
            call.enqueue(new Callback<DeleteResponse>() {
                @Override
                public void onResponse(@NonNull Call<DeleteResponse> call, @NonNull Response<DeleteResponse> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        DeleteResponse cartResponse = response.body();
                        if (context != null && cartResponse.getMessge() != null) {
                            Toast.makeText(CartActivity.this, ""+cartResponse.getMessge(), Toast.LENGTH_SHORT).show();

                        }
                        getCartList();
                    } else {
                        if (context != null)
                            Toast.makeText(CartActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DeleteResponse> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    if (context != null)
                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
      /*  cartBinding.tvTotalPrice.setText(context.getString(R.string.price, String.valueOf(getFinalPrice())));
        binding.tvTotalAmount.setText(context.getString(R.string.total_amount, String.valueOf(getFinalPrice())));

        if (getFinalPrice() > minOrder) {
            binding.tvDeliveryCharge.setText(context.getString(R.string.price, "0"));
        } else {
            binding.tvTotalAmount.setText(context.getString(R.string.total_amount, String.valueOf(getFinalPrice() + DELIVERY_CHARGE)));
            binding.tvDeliveryCharge.setText(context.getString(R.string.price, String.valueOf(DELIVERY_CHARGE)));
        }*/
    }
}