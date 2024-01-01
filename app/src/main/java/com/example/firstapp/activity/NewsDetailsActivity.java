package com.example.firstapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.News;

public class NewsDetailsActivity extends AppCompatActivity {

    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        news = getIntent().getParcelableExtra("data");

        if (news != null)
            Toast.makeText(this, "" + news.getTitle(), Toast.LENGTH_SHORT).show();
    }
}