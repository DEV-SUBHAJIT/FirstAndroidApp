package com.example.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.firstapp.adapter.PhotoAdapter;
import com.example.firstapp.R;
import com.example.firstapp.adapter.RecyclerViewAdapter;
import com.example.firstapp.model.Employee;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Intent getValue;
    String nName, nEmail, mPassword, userId;
    int pinCode;
    boolean loginStatus;
    TextView textView;
    ImageView /*ivImage1,*/ ivBanner, ivEmployee;
    LinearLayout rootLayout;
    RecyclerView rvEmployees;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        clickEvent();
        getApiData();
//        setEmployee();
        getPhotoList();

      /*  Picasso.get().load("https://wallpapers.com/images/hd/hd-vacation-house-in-the-beach-j4jasqgcc5ismew8.jpg")
                .placeholder(R.drawable.shopping_image)
                .into(ivImage1);*/

        Glide.with(HomeActivity.this).load("https://cdn.pixabay.com/photo/2014/09/14/18/04/dandelion-445228_640.jpg")
                .placeholder(R.drawable.shopping_image)
                .into(ivBanner);

        getValue = getIntent();

        if (getValue != null) {
            nName = getValue.getStringExtra("name");
            nEmail = getValue.getStringExtra("email");
            userId = getValue.getStringExtra("userId");
            mPassword = getValue.getStringExtra("password");
            pinCode = getValue.getIntExtra("pinCode", -1);
            loginStatus = getValue.getBooleanExtra("isLogin",false);
        } else {
            //Show Snackbar
            Snackbar.make(rootLayout, "Intent value not found", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void clickEvent() {
        ivEmployee.setOnClickListener(v -> {
//            startActivity(new Intent(this, EmployeeActivity.class));
            startActivity(new Intent(this, UserListActivity.class));
        });
    }

    private void initView() {
        rootLayout = findViewById(R.id.rootLayout);
        textView = findViewById(R.id.tvImageName);
//        ivImage1 = findViewById(R.id.imageView);
        ivBanner = findViewById(R.id.ivBanner);
        rvEmployees = findViewById(R.id.rvEmployees);
        progressBar = findViewById(R.id.progressBar);

        ivEmployee = findViewById(R.id.ivEmployee);
    }

    private void getPhotoList() {
        String photoApiUrl = "https://picsum.photos/v2/list?page=1&limit=10";
//        String photoApiUrl = "https://jsonplaceholder.typicode.com/albums/1/photos";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, photoApiUrl, response -> {
            try {
                progressBar.setVisibility(View.GONE);
                JSONArray apiResponse = new JSONArray(response);
                PhotoAdapter adapter = new PhotoAdapter(apiResponse);
                rvEmployees.setHasFixedSize(true);
//                rvEmployees.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
//                rvEmployees.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
                rvEmployees.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                rvEmployees.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(HomeActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        });
        queue.add(request);
    }

    private void getApiData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/todos/1";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int userId = jsonObject.getInt("userId");
                    int id = jsonObject.getInt("id");
                    String title = jsonObject.getString("title");
                    boolean completed = jsonObject.getBoolean("completed");

                    textView.setText("User id : "+userId + "\n"+
                                   "Id : "+ id + "\n"+
                           "title : " + title + "\n"+
                          "completed : "+   completed + "\n");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    private void setEmployee() {
        List<Employee> employeeList = new ArrayList<>();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(employeeList);
        rvEmployees.setHasFixedSize(true);
        rvEmployees.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        rvEmployees.setAdapter(adapter);
    }
}