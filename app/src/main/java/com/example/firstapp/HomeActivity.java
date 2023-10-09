package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private Intent getValue;
    String nName, nEmail, mPassword, userId;
    int pinCode;
    boolean loginStatus;
    TextView textView;
    LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rootLayout = findViewById(R.id.rootLayout);
        textView = findViewById(R.id.textView);

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
}