package com.example.uasmobileprogramming.utils;

import com.example.uasmobileprogramming.models.IMDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/API/InTheaters/[YOUR IMDB API KEY HERE]")
    Call<IMDBResponse> getAllMovies();
}
