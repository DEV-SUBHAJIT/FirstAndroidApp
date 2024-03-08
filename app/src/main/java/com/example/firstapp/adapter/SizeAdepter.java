package com.example.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.databinding.SizeBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.products.SizeDetails;

import java.util.List;

public class SizeAdepter extends  RecyclerView.Adapter<SizeAdepter.MyViewHolder>{



    private List<SizeDetails> sizeList;

    Context context;
    private OnItemClick itemClick;

    private int lastCheckedPosition = -1, selectedSizeDetailsId;

    public SizeAdepter(List<SizeDetails> sizeList,OnItemClick itemClick, int selectedSizeDetailsId) {
        this.sizeList = sizeList;
        this.itemClick = itemClick;
        this.selectedSizeDetailsId=selectedSizeDetailsId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        SizeBinding binding = SizeBinding.inflate(LayoutInflater.from(context),parent,false);

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeAdepter.MyViewHolder holder, int position) {
        holder.setData(sizeList.get(position),position);

    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SizeBinding binding;
        public MyViewHolder(@NonNull SizeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }


        public void setData(SizeDetails size, int position) {

            binding.tvSize.setText(size.getSize());

            binding.getRoot().setOnClickListener(v->{
                itemClick.onItemClick(position);
                lastCheckedPosition =  position;
                selectedSizeDetailsId = size.getId();
                notifyDataSetChanged();
            });


            if (lastCheckedPosition == position || selectedSizeDetailsId == size.getId()) {
                binding.tvSize.setBackgroundColor(context.getResources().getColor(R.color.grey_color, null));
            } else {
                binding.tvSize.setBackgroundColor(context.getResources().getColor(R.color.white, null));
            }
        }
    }
}
