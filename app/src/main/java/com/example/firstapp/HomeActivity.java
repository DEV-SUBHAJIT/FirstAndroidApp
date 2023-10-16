package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Intent getValue;
    String nName, nEmail, mPassword, userId;
    int pinCode;
    boolean loginStatus;
    TextView textView;
    ImageView ivImage1, ivBanner;
    LinearLayout rootLayout;
    RecyclerView rvEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rootLayout = findViewById(R.id.rootLayout);
        textView = findViewById(R.id.tvImageName);
        ivImage1 = findViewById(R.id.imageView);
        ivBanner = findViewById(R.id.ivBanner);
        rvEmployees = findViewById(R.id.rvEmployees);

        setEmployee();

        Picasso.get().load("https://wallpapers.com/images/hd/hd-vacation-house-in-the-beach-j4jasqgcc5ismew8.jpg")
                .placeholder(R.drawable.shopping_image)
                .into(ivImage1);

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

            textView.setText(nName+ "\n"+ nEmail + "\n"+ mPassword + "\n"+ pinCode + "\n"+ loginStatus);
            Toast.makeText(this, "" + nEmail, Toast.LENGTH_SHORT).show();
        } else {
            //Show Snackbar
            Snackbar.make(rootLayout, "Intent value not found", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void setEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Nilanjan Sarkar", "nilanjan32@gmail.com", "8565421456", "21", "Ichapur, North 24 Parganas, West Bengal, India"));
        employeeList.add(new Employee("Taps Hira", "tapashira@gmail.com", "76584698532", "29", "Ichapur, North 24 Parganas, West Bengal, India"));
        employeeList.add(new Employee("Subhajit Roy", "subhajitroy@gmail.com", "8620828385", "27", "Chakdah, Nadia, West Bengal, India"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(employeeList);
        rvEmployees.setHasFixedSize(true);
        rvEmployees.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        rvEmployees.setAdapter(adapter);
    }
}