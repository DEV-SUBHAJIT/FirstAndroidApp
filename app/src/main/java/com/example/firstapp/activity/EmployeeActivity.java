package com.example.firstapp.activity;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.R;
import com.example.firstapp.adapter.EmployeeAdapter;
import com.example.firstapp.model.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    RecyclerView rvEmployees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        initView();
        getEmployeeData();
    }

    private void getEmployeeData() {
        String url = "https://hub.dummyapis.com/employee?noofRecords=5&idStarts=1001";

        RequestQueue queue = Volley.newRequestQueue(EmployeeActivity.this);

        StringRequest request = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Initialize employee list
                List<Employee> employeeList = new ArrayList<>();

                //Declare Gson object
                Gson gson = new Gson();

                //Set employee list from api response
                employeeList =  gson.fromJson(response, new TypeToken<List<Employee>>() {}.getType());

                EmployeeAdapter adapter = new EmployeeAdapter(employeeList);
                rvEmployees.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmployeeActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void initView() {
        rvEmployees = findViewById(R.id.rvEmployees);
        rvEmployees.setHasFixedSize(true);
    }
}