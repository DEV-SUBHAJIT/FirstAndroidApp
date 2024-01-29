package com.example.firstapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.adapter.SqlEmployeeAdapter;
import com.example.firstapp.databinding.ActivitySqliteBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.helper.DBHandler;
import com.example.firstapp.model.SqlEmployee;

import java.util.List;

public class SQLiteActivity extends AppCompatActivity implements OnItemClick {
    private Context mContext = SQLiteActivity.this;
    private ActivitySqliteBinding binding;

    private DBHandler dbHandler;
    private int updateEmployeeId;
    private List<SqlEmployee> sqlEmployeeList;
    private SqlEmployeeAdapter adapter;
    private OnItemClick itemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySqliteBinding.inflate(LayoutInflater.from(mContext));
        setContentView(binding.getRoot());

        setDropdownData();
        dbHandler = new DBHandler(mContext);
        sqlEmployeeList = dbHandler.readEmployees();

        itemClick = position -> {
            binding.btnUpdate.setVisibility(View.VISIBLE);
            binding.btnSubmit.setVisibility(View.GONE);

            SqlEmployee employee = sqlEmployeeList.get(position);
            binding.etName.setText(employee.getName());
            binding.etAddress.setText(employee.getAddress());
            binding.etAge.setText(String.valueOf(employee.getAge()));
            binding.etAddress.setText(employee.getAddress());
            binding.etPhone.setText(employee.getPhone());
            binding.etSalary.setText(String.valueOf(employee.getSalary()));
            binding.etEmail.setText(employee.getEmail());

            updateEmployeeId = employee.getId();
        };

        //Adapter initialize
        adapter = new SqlEmployeeAdapter(sqlEmployeeList, itemClick, this::onItemClick);


        binding.rvEmployees.setHasFixedSize(true);
        binding.rvEmployees.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvEmployees.setAdapter(adapter);


        //Submit button click handel
        binding.btnSubmit.setOnClickListener(v -> {
            String mName = binding.etName.getText().toString();
            String mAddress = binding.etAddress.getText().toString();
            String mPhone = binding.etPhone.getText().toString();
            String mAge = binding.etAge.getText().toString();
            String mEmail = binding.etEmail.getText().toString();
            String mSalary = binding.etSalary.getText().toString();
            String mGender = binding.actvSpinner.getText().toString().trim();

            if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mAddress) || TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mAge) || TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mGender)) {
                Toast.makeText(mContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHandler.addNewEmployee(mName, mAddress, mPhone, Integer.parseInt(mAge), mEmail, Integer.parseInt(mSalary), mGender);

            sqlEmployeeList = dbHandler.readEmployees();
            adapter = new SqlEmployeeAdapter(sqlEmployeeList, itemClick, this::onItemClick);
            binding.rvEmployees.setAdapter(adapter);

            binding.etName.setText("");
            binding.etAddress.setText("");
            binding.etPhone.setText("");
            binding.etAge.setText("");
            binding.etEmail.setText("");
            binding.etSalary.setText("");
        });


        //Update button Click Handel
        binding.btnUpdate.setOnClickListener(v -> {
            String mName = binding.etName.getText().toString();
            String mAddress = binding.etAddress.getText().toString();
            String mPhone = binding.etPhone.getText().toString();
            String mAge = binding.etAge.getText().toString();
            String mEmail = binding.etEmail.getText().toString();
            String mSalary = binding.etSalary.getText().toString();
            String mGender = binding.actvSpinner.getText().toString().trim();

            if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mAddress) || TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mAge) || TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mGender)) {
                Toast.makeText(mContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHandler.updateEmployee(updateEmployeeId, mName, mAddress, mPhone, Integer.parseInt(mAge), mEmail, Integer.parseInt(mSalary), mGender);

            Toast.makeText(mContext, "Update successful", Toast.LENGTH_SHORT).show();

            sqlEmployeeList = dbHandler.readEmployees();
            adapter = new SqlEmployeeAdapter(sqlEmployeeList, itemClick, this);
            binding.rvEmployees.setAdapter(adapter);

            binding.etName.setText("");
            binding.etAddress.setText("");
            binding.etPhone.setText("");
            binding.etAge.setText("");
            binding.etEmail.setText("");
            binding.etSalary.setText("");

            binding.btnUpdate.setVisibility(View.GONE);
            binding.btnSubmit.setVisibility(View.VISIBLE);
        });

    }

    private void setDropdownData() {
        String[] items = new String[]{"Male", "Female", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        binding.actvSpinner.setAdapter(adapter);

        binding.actvSpinner.getText().toString();
    }

    @Override
    public void onItemClick(int position) {
        // Toast message on menu item clicked
        dbHandler.deleteEmployee(sqlEmployeeList.get(position).getId());

        Toast.makeText(mContext, sqlEmployeeList.get(position).getName() + " Delete successful", Toast.LENGTH_SHORT).show();

        sqlEmployeeList = dbHandler.readEmployees();
        adapter = new SqlEmployeeAdapter(sqlEmployeeList, itemClick, this);
        binding.rvEmployees.setAdapter(adapter);

        binding.btnUpdate.setVisibility(View.GONE);
        binding.btnSubmit.setVisibility(View.VISIBLE);
    }
}