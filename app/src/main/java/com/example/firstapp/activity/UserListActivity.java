package com.example.firstapp.activity;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstapp.R;
import com.example.firstapp.adapter.UserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserListActivity extends AppCompatActivity {
    Context context = UserListActivity.this;
    RecyclerView rvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initView();
        getUserData();
    }

    private void initView() {
        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);
        rvUsers.setLayoutManager(new LinearLayoutManager(context));
    }

    private void getUserData() {
        String url = "https://ecart.roywebtech.in/user";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isStatus = jsonResponse.getBoolean("Status");
                    if (isStatus){
                        JSONArray userList = jsonResponse.getJSONArray("User");
                        if (userList.length()>0){
                            UserAdapter adapter = new UserAdapter(userList);
                            rvUsers.setAdapter(adapter);
                        }
                    } else {
                        Toast.makeText(context, "No user found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
}