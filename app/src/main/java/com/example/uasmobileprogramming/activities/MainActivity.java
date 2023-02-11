package com.example.uasmobileprogramming.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.fragments.HomeFragment;
import com.example.uasmobileprogramming.fragments.LocationFragment;
import com.example.uasmobileprogramming.fragments.TheatersFragment;
import com.example.uasmobileprogramming.fragments.WatchlistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    };
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                    return true;

                case R.id.theaters:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new TheatersFragment()).commit();
                    return true;

                case R.id.watchlist:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new WatchlistFragment()).commit();
                    return true;

                case R.id.location:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new LocationFragment()).commit();
                    return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
            mAuth.signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}