package com.example.firstapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.R;
import com.example.firstapp.adapter.NewOrderAdapter;
import com.example.firstapp.adapter.OrderAdapter;
import com.example.firstapp.api_response.OrderResponse;
import com.example.firstapp.databinding.FragmentOrderBinding;
import com.example.firstapp.model.Orders;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

     private List<Orders> ordersList;

     private Context context;

     private FragmentOrderBinding binding;

    private int userId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderBinding.inflate(inflater, container ,false);
        SharedPreferences prefs = requireActivity().getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        userId = prefs.getInt("id",0);


        DataService service = RetrofitClient.getDataService();
        Call<OrderResponse> call = service.order(userId);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    OrderResponse orderResponse = response.body();

                    if (orderResponse.isStatus()) {
                        ordersList= orderResponse.getOrdersList();

                        binding.rvView.setHasFixedSize(true);
                        binding.rvView.setLayoutManager(new LinearLayoutManager(context));
                        NewOrderAdapter orderAdapter = new NewOrderAdapter(ordersList);
                        binding.rvView.setAdapter(orderAdapter);
                    }
                    else Toast.makeText(requireActivity(), "Order Found", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireActivity(), "Response failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(requireActivity(), "Server error", Toast.LENGTH_SHORT).show();
            }
        });
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
}