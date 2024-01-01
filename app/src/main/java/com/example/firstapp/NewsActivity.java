package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firstapp.adapter.NewsAdapter;
import com.example.firstapp.api_response.NewsResponse;
import com.example.firstapp.model.News;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.NewsRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    RecyclerView rvNews;
    private ProgressBar progressBar;
    private Context context = NewsActivity.this;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //Initialize progressbar
        progressBar = findViewById(R.id.progressBar);

        //Initialize recyclerview details
        rvNews = findViewById(R.id.rvNews);
        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(new GridLayoutManager(context, 2));

        getNews();
    }

    private void getNews() {
        DataService service = NewsRetrofitClient.getDataService();
        Call<NewsResponse> call = service.getNews(15, 0);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                progressBar.setVisibility(View.GONE);
                NewsResponse newsResponse = response.body();

                if (newsResponse.isStatus()) {
                    newsList = newsResponse.getNewsList();
                    if (newsList.size() > 0) {
                        rvNews.setAdapter(new NewsAdapter(newsList));
                    } else
                        Toast.makeText(NewsActivity.this, "No news available", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(NewsActivity.this, "" + newsResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(NewsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}