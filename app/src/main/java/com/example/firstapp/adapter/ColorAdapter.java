package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.databinding.ColorBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.Color;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ColorAdapter extends  RecyclerView.Adapter<ColorAdapter.MyViewHolder>{

    Context context;

    private List<Color>colorList;
    private OnItemClick itemClick;

    private int lastCheckedPosition = -1, selectedColorDetailsId;

    public ColorAdapter(List<Color> colorList,OnItemClick itemClick, int selectedColorDetailsId) {
        this.colorList = colorList;
        this.itemClick = itemClick;
        this.selectedColorDetailsId = selectedColorDetailsId;
    }

    @NonNull
    @Override
    public ColorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context= parent.getContext();
        ColorBinding binding = ColorBinding.inflate(LayoutInflater.from(context),parent,false);
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.color,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.MyViewHolder holder, int position) {

        holder.setData(colorList.get(position),position);

    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ColorBinding binding;
        public MyViewHolder(@NonNull ColorBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;


        }

        public void setData(Color color , int position) {

            binding.tvColorName.setText(color.getColorName());
            Picasso.get().load("https://"+color.getProductImageUrl()).into(binding.ivImage);


            binding.getRoot().setOnClickListener(v -> {
                itemClick.onItemClick(position);
                lastCheckedPosition = position;
                selectedColorDetailsId = color.getId();
                notifyDataSetChanged();
            });


            if (lastCheckedPosition == position || selectedColorDetailsId == color.getId()) {
                binding.cvBackground.setBackgroundColor(context.getResources().getColor(R.color.theme_color, null));
            } else {
                binding.cvBackground.setBackgroundColor(context.getResources().getColor(R.color.white, null));
            }
        }
    }
}
