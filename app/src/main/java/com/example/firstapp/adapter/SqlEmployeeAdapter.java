package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.EmployeeItemBinding;
import com.example.firstapp.model.SqlEmployee;

import java.util.List;

public class SqlEmployeeAdapter extends RecyclerView.Adapter<SqlEmployeeAdapter.MyViewHolder> {
    Context context;
    List<SqlEmployee> sqlEmployeeList;

    public SqlEmployeeAdapter(List<SqlEmployee> sqlEmployeeList) {
        this.sqlEmployeeList = sqlEmployeeList;
    }

    @NonNull
    @Override
    public SqlEmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        EmployeeItemBinding binding = EmployeeItemBinding.inflate(LayoutInflater.from(context));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SqlEmployeeAdapter.MyViewHolder holder, int position) {
        holder.setData(sqlEmployeeList.get(position));
    }

    @Override
    public int getItemCount() {
        return sqlEmployeeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EmployeeItemBinding binding;

        public MyViewHolder(@NonNull EmployeeItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setData(SqlEmployee sqlEmployee) {
            binding.tvName.setText(sqlEmployee.getName());
            binding.tvAddress.setText(sqlEmployee.getAddress());
            binding.tvAge.setText(String.valueOf(sqlEmployee.getAge()));
            binding.tvEmail.setText(sqlEmployee.getEmail());
            binding.tvSalary.setText(String.valueOf(sqlEmployee.getSalary()));
            binding.tvContactNumber.setText(sqlEmployee.getPhone());

        }
    }
}
