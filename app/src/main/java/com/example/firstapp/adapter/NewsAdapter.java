package com.example.firstapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.activity.NewsDetailsActivity;
import com.example.firstapp.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> newsList;
    private Context context;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        News news = newsList.get(position);

        holder.tvTitle.setText(HtmlCompat.fromHtml(news.getTitle(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.tvDesc.setText(HtmlCompat.fromHtml(news.getNewsContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        Picasso.get().load(news.getThumbnailUrl()).into(holder.ivThumbnail);

        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, NewsDetailsActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivThumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
        }
    }
}
