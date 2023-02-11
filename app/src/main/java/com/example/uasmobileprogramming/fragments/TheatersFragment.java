package com.example.uasmobileprogramming.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.activities.RentActivity;
import com.example.uasmobileprogramming.adapters.MovieAdapter;
import com.example.uasmobileprogramming.adapters.TheaterAdapter;
import com.example.uasmobileprogramming.models.Movie;
import com.example.uasmobileprogramming.models.Theater;
import com.example.uasmobileprogramming.models.TheaterInfo;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TheatersFragment extends Fragment {
    ExtendedFloatingActionButton rentFab;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDB;
    DatabaseReference theatersRef;
    RecyclerView rvTheaters;
    TheaterAdapter theaterAdapter;
    ArrayList<Theater> theaterList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_theaters, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDB = FirebaseDatabase.getInstance("https://uas-mobprog-dd9a6-default-rtdb.asia-southeast1.firebasedatabase.app/");

        rvTheaters = v.findViewById(R.id.rv_items);
        theaterAdapter = new TheaterAdapter(theaterList, getActivity());
        rvTheaters.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTheaters.setAdapter(theaterAdapter);

        theatersRef = firebaseDB.getReference("users").child(currentUser.getUid()).child("theaters");
        theatersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                theaterList.clear();
                for(DataSnapshot theaterSnapshot : snapshot.getChildren()){
                    Theater t = theaterSnapshot.getValue(Theater.class);
                    theaterList.add(t);
                }
                theaterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rentFab = v.findViewById(R.id.rent_fab);
        rentFab.setOnClickListener(view -> {
            DatabaseReference movieRef = firebaseDB.getReference("users").child(currentUser.getUid()).child("movies");
            movieRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() == null){
                        Toast.makeText(getActivity(), "Please add a movie to watchlist before renting theater", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(new Intent(getActivity(), RentActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Fail to get data", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return v;
    }
}