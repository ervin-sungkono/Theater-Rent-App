package com.example.uasmobileprogramming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.adapters.TheaterAdapter;
import com.example.uasmobileprogramming.models.Movie;
import com.example.uasmobileprogramming.models.Theater;
import com.example.uasmobileprogramming.models.TheaterInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDB;
    DatabaseReference theaterRef;
    DatabaseReference movieRef;
    DatabaseReference userRef;
    Spinner cinemaSpinner, theaterSpinner, movieSpinner;
    ImageView ivThumbnail;
    EditText dateField;
    TextView tvDuration, tvPrice;
    Button rentButton;
    ArrayList<TheaterInfo> theaterInfos = new ArrayList<>();
    ArrayList<Movie> movieList = new ArrayList<>();
    String[] cinemaList = {"cgp-alpha", "cgp-beta"};
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDB = FirebaseDatabase.getInstance("https://uas-mobprog-dd9a6-default-rtdb.asia-southeast1.firebasedatabase.app/");

        ivThumbnail = findViewById(R.id.theater_thumbnail);
        tvDuration = findViewById(R.id.tv_duration);
        tvPrice = findViewById(R.id.tv_price);
        rentButton = findViewById(R.id.rent_btn);
        cinemaSpinner = findViewById(R.id.spinner_cinemas);
        theaterSpinner = findViewById(R.id.spinner_theaters);
        movieSpinner = findViewById(R.id.spinner_movies);
        dateField = findViewById(R.id.et_date);

        dateField.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RentActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        String dateString = String.format("%02d-%02d-%d", day, (monthOfYear + 1), year1);
                        dateField.setText(dateString);
                    },
                    year, month, day);

            datePickerDialog.show();
        });

        movieSpinner.setOnItemSelectedListener(this);
        theaterSpinner.setOnItemSelectedListener(this);
        cinemaSpinner.setOnItemSelectedListener(this);

        theaterRef = firebaseDB.getReference("cinemas").child(cinemaList[0]);
        setTheatersData();

        movieRef = firebaseDB.getReference("users").child(currentUser.getUid()).child("movies");
        setMovieData();

        userRef = firebaseDB.getReference("users").child(currentUser.getUid()).child("theaters");

        rentButton.setOnClickListener(view -> {
            TheaterInfo ti = theaterInfos.get(theaterSpinner.getSelectedItemPosition());
            String theaterName = ti.getName();
            String imageUrl = ti.getImageUrl();
            String cinemaLoc = cinemaSpinner.getSelectedItem().toString();
            Movie m = movieList.get(movieSpinner.getSelectedItemPosition());
            int runtimeMins = Integer.parseInt(m.getRuntimeMins());
            String rentDuration = (runtimeMins/60) + "h" + (runtimeMins % 60) + "m";
            int price = m.getPrice();
            String rentDate = dateField.getText().toString();

            if(rentDate.length() == 0){
                Toast.makeText(this, "Please fill in the date", Toast.LENGTH_SHORT).show();
                return;
            }

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long dataCount = snapshot.getChildrenCount();
                    String id = "tt" + dataCount;
                    userRef.child(id).setValue(new Theater(theaterName, imageUrl, cinemaLoc, rentDate, rentDuration, price, m));
                    Toast.makeText(RentActivity.this, "Rent successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RentActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RentActivity.this, "Fail to add data", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_cinemas:
                String cinema = cinemaList[i];
                theaterRef = firebaseDB.getReference("cinemas").child(cinema);
                setTheatersData();
                break;
            case R.id.spinner_theaters:
                TheaterInfo ti = theaterInfos.get(i);
                Picasso.Builder builder = new Picasso.Builder(this);
                builder.downloader(new OkHttp3Downloader(this));
                builder.build().load(ti.getImageUrl())
                        .resize(320,180)
                        .placeholder(R.drawable.load_image)
                        .error(R.drawable.no_image_placeholder_svg)
                        .into(ivThumbnail);
                break;
            case R.id.spinner_movies:
                Movie m = movieList.get(i);
                int runtimeMins = Integer.parseInt(m.getRuntimeMins());
                tvDuration.setText("Duration\n" + (runtimeMins/60) + "h" + (runtimeMins % 60) + "m");
                tvPrice.setText("Price\nIDR " + String.format("%,d", m.getPrice()));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setTheatersData(){
        theaterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                theaterInfos.clear();
                for (DataSnapshot theatherSnapshot : snapshot.getChildren()){
                    TheaterInfo theaterInfo = theatherSnapshot.getValue(TheaterInfo.class);
                    theaterInfos.add(theaterInfo);
                }
                ArrayAdapter<TheaterInfo> theaterInfoArrayAdapter= new ArrayAdapter<TheaterInfo>(getApplicationContext(), android.R.layout.simple_spinner_item, theaterInfos);
                theaterInfoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                theaterSpinner.setAdapter(theaterInfoArrayAdapter);
                theaterSpinner.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RentActivity.this, "Fail to read data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMovieData(){
        movieRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movieList.clear();
                for(DataSnapshot movieSnapshot : snapshot.getChildren()){
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    movieList.add(movie);
                }
                ArrayAdapter<Movie> movieSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, movieList);
                movieSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                movieSpinner.setAdapter(movieSpinnerAdapter);
                movieSpinner.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RentActivity.this, "Fail to read data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}