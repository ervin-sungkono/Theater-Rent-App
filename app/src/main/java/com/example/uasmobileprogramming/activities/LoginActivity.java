package com.example.uasmobileprogramming.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;
    private TextView registerLink;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        emailField = findViewById(R.id.et_email);
        passwordField = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.login_btn);
        registerLink = findViewById(R.id.register_link);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (email.length() == 0 || password.length() == 0) {
                Toast.makeText(this, "Email and password cannot be empty!", Toast.LENGTH_SHORT).show();
            }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            }else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if(!task.isSuccessful()){
                                Toast.makeText(this, "Email or password is incorrect.", Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        });
            }
        });

        registerLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }
}