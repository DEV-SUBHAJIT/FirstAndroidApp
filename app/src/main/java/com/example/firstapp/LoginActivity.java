package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegister;
    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    String email, password;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initilize
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);


        btnLogin.setOnClickListener(v -> {
            email = etEmail.getText().toString().trim();
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
                    });

        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


    }

    private void getUserDetails(String uId) {
        myRef.child(uId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("name", user.getName());
                intent.putExtra("email", email);
                intent.putExtra("password", user.getPassword());
                intent.putExtra("userId", uId);
                intent.putExtra("pinCode", 700091);
                intent.putExtra("isLogin", true);

                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "" + String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}