package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.databinding.CategoryItemBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.Category;
import com.example.firstapp.model.SubCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    Context context;
    private List<SubCategory> subCategoryList;
    private OnItemClick itemClick;

    public SubCategoryAdapter(List<SubCategory> subCategoryList, OnItemClick itemClick) {
        this.subCategoryList = subCategoryList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        CategoryItemBinding binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder holder, int position) {
        holder.setData(subCategoryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CategoryItemBinding binding;
        public MyViewHolder(@NonNull CategoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(SubCategory subCategoryList, int position) {
            binding.tvCategoryName.setText(subCategoryList.getSubCategoryName());
            String imageUrl = "https://"+subCategoryList.getSubCategoryImage();
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher).into(binding.ivCategoryImage);

            binding.getRoot().setOnClickListener(v -> {
                itemClick.onItemClick(position);
            });
        }
    }
}
