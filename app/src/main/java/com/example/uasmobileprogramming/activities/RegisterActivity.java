package com.example.uasmobileprogramming.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.models.Theater;
import com.example.uasmobileprogramming.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDB;
    private EditText emailField, usernameField, passwordField, confPassField;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance("https://uas-mobprog-dd9a6-default-rtdb.asia-southeast1.firebasedatabase.app/");

        emailField = findViewById(R.id.et_email);
        usernameField = findViewById(R.id.et_username);
        passwordField = findViewById(R.id.et_password);
        confPassField = findViewById(R.id.et_conf_password);
        registerButton = findViewById(R.id.register_btn);

        registerButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            String confPass = confPassField.getText().toString();

            if(email.length() == 0 || username.length() == 0 || password.length() == 0){
                Toast.makeText(this, "All credentials must be filled.", Toast.LENGTH_SHORT).show();
            }else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            }else if (username.length() < 5){
                Toast.makeText(this, "Username must be at least 5 characters.", Toast.LENGTH_SHORT).show();
            }else if (password.length() < 7){
                Toast.makeText(this, "Password must be at least 7 characters.", Toast.LENGTH_SHORT).show();
            }else if (!password.equals(confPass)){
                Toast.makeText(this, "Password does not match confirm password.", Toast.LENGTH_SHORT).show();
            }else{
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if(!task.isSuccessful()){
                                Toast.makeText(this, "Register failed.", Toast.LENGTH_SHORT).show();
                            }else{
                                mAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(this, task2 -> {
                                            if(!task2.isSuccessful()){
                                                Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show();
                                            }else{
                                                String id = mAuth.getCurrentUser().getUid();
                                                DatabaseReference userRef = firebaseDB.getReference("users").child(id);
                                                userRef.setValue(new User(email, username, null));
                                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                finish();
                                            }
                                        });
                            }
                        });
            }
        });
    }
}