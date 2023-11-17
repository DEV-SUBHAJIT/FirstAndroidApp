package com.example.firstapp.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private TextInputEditText eietTitle, eietBody, eietUserId;
    private MaterialButton mbtnSave;

    private String mTitle, mBody, mUserId;
    private TextView tvResponse;
    private ProgressBar progressBar;

    private String url = "https://jsonplaceholder.typicode.com/posts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
        clickListener();
    }

    private void initView() {
        eietTitle = findViewById(R.id.eietTitle);
        eietBody = findViewById(R.id.eietBody);
        eietUserId = findViewById(R.id.eietUserId);
        mbtnSave = findViewById(R.id.mbtnSave);
        tvResponse = findViewById(R.id.tvResponse);
        progressBar = findViewById(R.id.progressBar);

    }

    private void clickListener() {
        mbtnSave.setOnClickListener(view -> {
            mTitle = eietTitle.getText().toString();
            mBody = eietBody.getText().toString();
            mUserId = eietUserId.getText().toString();

            if (TextUtils.isEmpty(mTitle)) {
                eietTitle.setError("Enter title");
                return;
            }
            if (TextUtils.isEmpty(mBody)) {
                eietBody.setError("Enter body message");
                return;
            }
            if (TextUtils.isEmpty(mUserId)) {
                eietUserId.setError("Enter user id");
                return;
            }

            try {
                progressBar.setVisibility(View.VISIBLE);
                saveData();
            } catch (JSONException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void saveData() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, (Response.Listener<String>) response -> {
            tvResponse.setText(response);
            progressBar.setVisibility(View.GONE);
        }, error -> {
            progressBar.setVisibility(View.GONE);
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> body = new HashMap<>();
                body.put("title", mTitle);
                body.put("body", mBody);
                body.put("userId", mUserId);
                return body;
            }
        };

        requestQueue.add(request);

    }
}