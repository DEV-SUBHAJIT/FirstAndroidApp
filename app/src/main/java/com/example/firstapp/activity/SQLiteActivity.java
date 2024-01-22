package com.example.firstapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.databinding.ActivitySqliteBinding;
import com.example.firstapp.helper.DBHandler;

public class SQLiteActivity extends AppCompatActivity {
    private Context mContext = SQLiteActivity.this;
    private ActivitySqliteBinding binding;

    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySqliteBinding.inflate(LayoutInflater.from(mContext));
        setContentView(binding.getRoot());

        dbHandler = new DBHandler(mContext);


        binding.btnSubmit.setOnClickListener(v -> {
            String mName = binding.etName.getText().toString();
            String mAddress = binding.etAddress.getText().toString();
            String mPhone = binding.etPhone.getText().toString();
            String mAge = binding.etAge.getText().toString();

            if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mAddress) || TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mAge)) {
                Toast.makeText(mContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHandler.addNewData(mName, mAddress, mPhone, Integer.parseInt(mAge));

            binding.etName.setText("");
            binding.etAddress.setText("");
            binding.etPhone.setText("");
            binding.etAge.setText("");
        });

    }
}