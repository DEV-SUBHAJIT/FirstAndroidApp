package com.example.firstapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.R;
import com.example.firstapp.api_response.UserRespons;
import com.example.firstapp.model.User;
import com.example.firstapp.model.UserModel;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegister;
    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    private CheckBox cvRemember;

    String email, password;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences("LoginDetails", MODE_PRIVATE);

        // The value will be default as empty string because for the very
        // first time when the app is opened, there is nothing to show
        boolean isLogin = pref.getBoolean("isLoggedIn", false);

        if (isLogin) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        //Initilize
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        cvRemember = findViewById(R.id.cvRemember);

        btnLogin.setOnClickListener(v -> {
           /* Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();*/
          /*  email = etEmail.getText().toString().trim();
            password = etPassword.getText().toString().trim();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uId = user.getUid();
                            getUserDetails(uId);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });*/

            login();

        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        cvRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked){
                    editor.putBoolean("isLoggedIn", true);
                } else {
                    editor.remove("isLoggedIn");
                }
                editor.apply();

            }
        });

    }

    private void login() {
        String mobile  = etEmail.getText().toString();
        if (TextUtils.isEmpty(mobile)|| mobile.length() != 10){
            etEmail.setError("Enter proper value");
            return;
        }

        DataService service = RetrofitClient.getDataService();
        Call<UserRespons> call = service.getUser(mobile);

        call.enqueue(new Callback<UserRespons>() {
            @Override
            public void onResponse(Call<UserRespons> call, Response<UserRespons> response) {
                if (response.isSuccessful() && response.body()!=null){
                    UserRespons userRespons = response.body();
                    if (userRespons.isStatus()){
                       UserModel userModel= userRespons.getUserModel();
                       if (userModel!= null){
                           //Shore details in Shared Preference
                           SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                           SharedPreferences.Editor editor = sharedPreferences.edit();

                           editor.putString("fullName", userModel.getFullName());
                           editor.putString("email", userModel.getEmail());
                           editor.putString("mobileNumber", userModel.getMobileNumber());
                           editor.putString("gender", userModel.getGender());
                           editor.putString("imageUrl", userModel.getImageUrl());
                           editor.putInt("id", userModel.getId());
                           editor.putBoolean("isLoggedIn", true);
                           editor.apply();

                           Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                           startActivity(intent);
                           finish();

                       } else Toast.makeText(getApplicationContext(), "No useer found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRespons> call, Throwable t) {

            }
        });
    }

    private void getUserDetails(String uId) {
        myRef.child(uId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);

                if (cvRemember.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("UserName", user.getName());
                    editor.putString("UserEmail", user.getEmail());
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                }


                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("name", user.getName());
                intent.putExtra("email", email);
                intent.putExtra("password", user.getPassword());
                intent.putExtra("userId", uId);
                intent.putExtra("pinCode", 700091);
                intent.putExtra("isLogin", true);

                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "" + task.getResult().getValue(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}