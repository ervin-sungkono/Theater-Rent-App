package com.example.uasmobileprogramming.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IMDBResponse {
    @Expose
    @SerializedName("items")
    private ArrayList<Movie> items;

    public ArrayList<Movie> getItems() {
        return items;
    }

    @NonNull
    @Override
    public String toString() {
        return items.toString();
    }
}
