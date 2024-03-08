package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.model.Orders;

import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> {
    Context context;
    List<Orders> ordersList;

    public NewOrderAdapter(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public NewOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        com.example.firstapp.databinding.OrderBinding binding = com.example.firstapp.databinding.OrderBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewOrderAdapter.MyViewHolder holder, int position) {
        holder.setData(ordersList.get(position));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        com.example.firstapp.databinding.OrderBinding binding;
        public MyViewHolder(@NonNull com.example.firstapp.databinding.OrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(Orders orders) {
            binding.tvProductName.setText(orders.getContactNumber());
        }
    }
}
