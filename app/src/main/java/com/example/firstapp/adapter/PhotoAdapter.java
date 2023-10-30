package com.example.firstapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    Context context;
    JSONArray photoArray;

    public PhotoAdapter(JSONArray photoArray) {
        this.photoArray = photoArray;
    }

    @NonNull
    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.MyViewHolder holder, int position) {
        try {
            holder.setData(photoArray.getJSONObject(position));
        } catch (JSONException e) {
            Log.d("JsonError", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return photoArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }

        public void setData(JSONObject photo) {
            try {
                Picasso.get().load(photo.getString("download_url")).placeholder(R.mipmap.ic_launcher).into(ivImage);
                tvTitle.setText(photo.getString("author"));
            } catch (JSONException e){
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
