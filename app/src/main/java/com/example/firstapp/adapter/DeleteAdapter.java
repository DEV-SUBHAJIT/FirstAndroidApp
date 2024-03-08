package com.example.firstapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.model.DeleteCart;

import java.util.List;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.MyViewHolder>{

    List<DeleteCart> deleteList;

    public DeleteAdapter(List<DeleteCart> deleteList) {
        this.deleteList = deleteList;
    }

    @NonNull
    @Override
    public DeleteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAdapter.MyViewHolder holder, int position) {
        holder.setData(deleteList.get(position));

    }

    @Override
    public int getItemCount() {
        return deleteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ivRemove,ivAdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRemove=itemView.findViewById(R.id.ivRemove);
            ivAdd=itemView.findViewById(R.id.ivAdd);
        }

        public void setData(DeleteCart delete) {
            ivAdd.setText(delete.getId());
        }
    }
}
