package com.example.uasmobileprogramming.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.models.IMDBResponse;
import com.example.uasmobileprogramming.models.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ImageView ivThumbnail;
    TextView tvName, tvDesc, tvIMDBRating, tvRatingCount, tvDuration, tvContentRating, tvCategory, tvDirectors, tvStars;
    Button addButton, removeButton;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie_details");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDB = FirebaseDatabase.getInstance("https://uas-mobprog-dd9a6-default-rtdb.asia-southeast1.firebasedatabase.app/");

        ivThumbnail = findViewById(R.id.iv_item_image);
        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_description);
        tvIMDBRating = findViewById(R.id.tv_imdb_rating);
        tvRatingCount = findViewById(R.id.tv_rating_count);
        tvDuration = findViewById(R.id.tv_duration);
        tvContentRating = findViewById(R.id.tv_content_rating);
        tvCategory = findViewById(R.id.tv_category);
        tvDirectors = findViewById(R.id.tv_directors);
        tvStars = findViewById(R.id.tv_stars);
        addButton = findViewById(R.id.add_btn);
        removeButton = findViewById(R.id.remove_btn);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(movie.getImageUrl())
                .resize(300,400)
                .placeholder(R.drawable.load_image)
                .error(R.drawable.no_image_placeholder_svg)
                .into(ivThumbnail);

        tvName.setText(movie.getTitle());
        tvDesc.setText(movie.getPlot());
        tvIMDBRating.setText(movie.getImDbRating() + "/10");
        tvRatingCount.setText(movie.getImDbRatingCount() + " ratings");
        int runtimeMins = Integer.parseInt(movie.getRuntimeMins());
        tvDuration.setText((runtimeMins/60) + "h" + (runtimeMins % 60) + "m");
        tvContentRating.setText(movie.getContentRating());
        tvCategory.setText(movie.getGenres());
        tvDirectors.setText("Directors: " + movie.getDirectors());
        tvStars.setText("Main Cast: " + movie.getStars());

        DatabaseReference movieRef = firebaseDB.getReference("users").child(currentUser.getUid()).child("movies").child(movie.getId());
        movieRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    addButton.setVisibility(View.GONE);
                    removeButton.setVisibility(View.VISIBLE);
                }else{
                    addButton.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailActivity.this, "Data Error :: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(v -> {
            movieRef.setValue(movie);
            Toast.makeText(DetailActivity.this, "Movie added!", Toast.LENGTH_SHORT).show();
        });

        removeButton.setOnClickListener(v -> {
            movieRef.removeValue();
            Toast.makeText(DetailActivity.this, "Movie removed from watchlist!", Toast.LENGTH_SHORT).show();
        });
    }
}