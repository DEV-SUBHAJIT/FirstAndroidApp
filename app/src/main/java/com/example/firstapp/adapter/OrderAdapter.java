package com.example.firstapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.FragmentOrderBinding;
import com.example.firstapp.databinding.OrderBinding;
import com.example.firstapp.databinding.ProductsBinding;
import com.example.firstapp.fragment.OrderFragment;
import com.example.firstapp.model.Color;
import com.example.firstapp.model.Orders;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    List<Orders> ordersList;

    public OrderAdapter(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        OrderBinding binding = OrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        holder.setData(ordersList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        OrderBinding binding;

        public MyViewHolder(@NonNull OrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(Orders orders, int position) {
            Toast.makeText(context, ""+orders.getContactName(), Toast.LENGTH_SHORT).show();

            binding.tvProductName.setText(orders.getOrderDetailsList().get(0).getProductName() + "+(" + (orders.getOrderDetailsList().size() - 1) + " item)");


        }
    }
}
