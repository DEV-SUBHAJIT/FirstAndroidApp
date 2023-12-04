package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.databinding.CategoryItemBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    private List<Category> categoryList;
    private OnItemClick itemClick;

    public CategoryAdapter(List<Category> categoryList, OnItemClick itemClick) {
        this.categoryList = categoryList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        CategoryItemBinding binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        holder.setData(categoryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CategoryItemBinding binding;
        public MyViewHolder(@NonNull CategoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(Category category, int position) {
            binding.tvCategoryName.setText(category.getCategoryName());
            String imageUrl = "https://"+category.getCategoryImage();
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher).into(binding.ivCategoryImage);

            binding.getRoot().setOnClickListener(v -> {
                itemClick.onItemClick(position);
            });
        }
    }
}
