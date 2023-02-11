package com.example.uasmobileprogramming.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.adapters.MovieAdapter;
import com.example.uasmobileprogramming.models.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WatchlistFragment extends Fragment {
    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDB;
    private ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_watchlist, container, false);

        firebaseDB = FirebaseDatabase.getInstance("https://uas-mobprog-dd9a6-default-rtdb.asia-southeast1.firebasedatabase.app/");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference movieRef = firebaseDB.getReference("users").child(currentUser.getUid()).child("movies");

        rvMovies = v.findViewById(R.id.rv_items);
        movieAdapter = new MovieAdapter(movieList, getActivity());
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(movieAdapter);

        movieRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movieList.clear();
                for(DataSnapshot movieSnapshot : snapshot.getChildren()){
                    Movie movie = movieSnapshot.getValue(Movie.class);
                    movieList.add(movie);
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to read data", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}