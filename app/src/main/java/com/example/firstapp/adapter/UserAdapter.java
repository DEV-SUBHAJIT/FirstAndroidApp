package com.example.firstapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    JSONArray userList;

    public UserAdapter(JSONArray userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        try {
            holder.setUser((JSONObject) userList.get(position));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return userList.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvMobile, tvGender, tvCreateDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvCreateDate = itemView.findViewById(R.id.tvCreateDate);
        }

        public void setUser(JSONObject user) throws JSONException {
            tvName.setText(user.getString("fullName"));
            tvEmail.setText(user.getString("email"));
            tvMobile.setText(user.getString("mobileNumber"));
            tvGender.setText(user.getString("gender"));
            tvCreateDate.setText(user.getString("createdAt"));
        }
    }
}
