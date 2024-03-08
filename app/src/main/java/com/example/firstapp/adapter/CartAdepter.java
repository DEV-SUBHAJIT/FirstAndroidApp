package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.databinding.CartItemBinding;
import com.example.firstapp.databinding.CartViewBinding;
import com.example.firstapp.event.CartListener;
import com.example.firstapp.model.Carts;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdepter extends RecyclerView.Adapter<CartAdepter.MyViewHolder> {

    Context context;

    List<Carts>cartsList;
    CartListener cartListener;

    public CartAdepter(List<Carts> cartsList, Context context, CartListener cartListener) {
        this.cartsList = cartsList;
        this.context= context;
        this.cartListener= cartListener;
    }

    @NonNull
    @Override
    public CartAdepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view,parent,false);

        CartItemBinding binding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdepter.MyViewHolder holder, int position) {
        holder.setData(cartsList.get(position),position);

    }

    @Override
    public int getItemCount() {
        return cartsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CartItemBinding binding;
        public MyViewHolder(@NonNull CartItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(Carts carts, int position) {
            binding.tvProductName.setText(carts.getProductName());
            binding.tvSize.setText("Size:"+carts.getSize());
            binding.tvPrice.setText(context.getString(R.string.product_price,String.valueOf(carts.getProductMRP())));
            Picasso.get().load("https://"+carts.getImageUrl()).into(binding.ivProductImage);

            String.valueOf(carts.getAvailableProduct());
            if (carts.getAvailableProduct() > 0) {
                if (carts.getAvailableProduct() > carts.getTotalItem()) {
                    binding.tvTotalItem.setText(String.valueOf(carts.getTotalItem()));
                } else binding.tvTotalItem.setText(String.valueOf(carts.getAvailableProduct()));
            } else {
                binding.tvOutOfStock.setVisibility(View.VISIBLE);
            }

            binding.ivAdd.setOnClickListener(v -> {
                int i = getTotalItem() + 1; // 0+1
                binding.tvTotalItem.setText(String.valueOf(i));
                cartsList.get(getAdapterPosition()).setTotalItem(i);
                cartListener.onItemClick(true, carts);
            });

            binding.ivRemove.setOnClickListener(v -> {
                if (getTotalItem() == 1) {
                    binding.tvTotalItem.setText("0");
                    cartsList.get(getAdapterPosition()).setTotalItem(0);
                    cartListener.onItemClick(false, carts);
                } else if (getTotalItem() != 0) {
                    int i = getTotalItem() - 1;
                    binding.tvTotalItem.setText(String.valueOf(i));
                    cartsList.get(getAdapterPosition()).setTotalItem(i);
                    cartListener.onItemClick(false, carts);
                }
            });

        }
        private int getTotalItem() {
            String s = binding.tvTotalItem.getText().toString().equals("") ? "0" : binding.tvTotalItem.getText().toString();
            return Integer.parseInt(s);
        }
    }
}
