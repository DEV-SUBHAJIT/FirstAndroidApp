package com.example.firstapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.adapter.SqlEmployeeAdapter;
import com.example.firstapp.databinding.ActivitySqliteBinding;
import com.example.firstapp.helper.DBHandler;
import com.example.firstapp.model.SqlEmployee;

import java.util.List;

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

        List<SqlEmployee> sqlEmployeeList = dbHandler.readEmployees();

        binding.rvEmployees.setHasFixedSize(true);
        binding.rvEmployees.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.rvEmployees.setAdapter(new SqlEmployeeAdapter(sqlEmployeeList));


        binding.btnSubmit.setOnClickListener(v -> {
            String mName = binding.etName.getText().toString();
            String mAddress = binding.etAddress.getText().toString();
            String mPhone = binding.etPhone.getText().toString();
            String mAge = binding.etAge.getText().toString();
            String mEmail=binding.etEmail.getText().toString();
            String mSalary = binding.etSalary.getText().toString();

            if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mAddress) || TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mAge)||TextUtils.isEmpty(mEmail)) {
                Toast.makeText(mContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHandler.addNewData(mName, mAddress, mPhone, Integer.parseInt(mAge),mEmail, Integer.parseInt(mSalary));

            binding.etName.setText("");
            binding.etAddress.setText("");
            binding.etPhone.setText("");
            binding.etAge.setText("");
            binding.etEmail.setText("");
            binding.etSalary.setText("");
        });

    }
}