package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.databinding.EmployeeItemBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.helper.DBHandler;
import com.example.firstapp.model.SqlEmployee;

import java.util.List;

public class SqlEmployeeAdapter extends RecyclerView.Adapter<SqlEmployeeAdapter.MyViewHolder> {
    Context context;
    List<SqlEmployee> sqlEmployeeList;
    private OnItemClick onItemClick;
    private OnItemClick onItemDeleteClick;

    public SqlEmployeeAdapter(List<SqlEmployee> sqlEmployeeList, OnItemClick onItemClick, OnItemClick onItemDeleteClick) {
        this.sqlEmployeeList = sqlEmployeeList;
        this.onItemClick = onItemClick;
        this.onItemDeleteClick = onItemDeleteClick;
    }

    @NonNull
    @Override
    public SqlEmployeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        EmployeeItemBinding binding = EmployeeItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SqlEmployeeAdapter.MyViewHolder holder, int position) {
        holder.setData(sqlEmployeeList.get(position), position);
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

        public void setData(SqlEmployee sqlEmployee, int position) {
            binding.tvName.setText(sqlEmployee.getName());
            binding.tvAddress.setText(sqlEmployee.getAddress());
            binding.tvAge.setText(String.valueOf(sqlEmployee.getAge()));
            binding.tvEmail.setText(sqlEmployee.getEmail());
            binding.tvSalary.setText(String.valueOf(sqlEmployee.getSalary()));
            binding.tvContactNumber.setText(sqlEmployee.getPhone());

            binding.card.setVisibility(View.GONE);

            binding.getRoot().setOnClickListener(v -> onItemClick.onItemClick(position));

            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, binding.tvName);

                    // Inflating popup menu from popup_menu.xml file
                    popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                         onItemDeleteClick.onItemClick(position);
                            return true;
                        }
                    });
                    // Showing the popup menu
                    popupMenu.show();
                    return true;
                }
            });
        }
    }
}
