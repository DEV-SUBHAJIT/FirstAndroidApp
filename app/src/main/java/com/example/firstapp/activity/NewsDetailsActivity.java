package com.example.firstapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.model.News;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    News news;
    private ImageView ivImage;
    private TextView tvTitle;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        //Initialize
        ivImage = findViewById(R.id.ivImage);
        tvTitle = findViewById(R.id.tvTitle);
        webView = findViewById(R.id.webView);


        news = getIntent().getParcelableExtra("news");

        if (news == null)
            Toast.makeText(this, "No news found" , Toast.LENGTH_SHORT).show();
        else {
            Picasso.get().load(news.getThumbnailUrl()).into(ivImage);
            tvTitle.setText(HtmlCompat.fromHtml(news.getTitle(), HtmlCompat.FROM_HTML_MODE_LEGACY));
           webView.loadDataWithBaseURL("",news.getNewsContent(),"text/html","UTF-8","");
        }
    }
}