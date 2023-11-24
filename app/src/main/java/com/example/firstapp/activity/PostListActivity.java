package com.example.firstapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.adapter.PostAdapter;
import com.example.firstapp.databinding.ActivityPostListBinding;
import com.example.firstapp.model.Post;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends AppCompatActivity {
    private ActivityPostListBinding binding;
    private Context context = PostListActivity.this;
    private List<Post> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostListBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());

        getData();
    }

    private void getData() {
        DataService service = RetrofitClient.getDataService();
        Call<List<Post>> call = service.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();


                    binding.rvPost.setHasFixedSize(true);
                    binding.rvPost.setLayoutManager(new LinearLayoutManager(context));
                    binding.rvPost.setAdapter(new PostAdapter(posts));

                } else {
                    Toast.makeText(PostListActivity.this, "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(PostListActivity.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}