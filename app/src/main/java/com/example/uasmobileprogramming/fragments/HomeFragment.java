package com.example.uasmobileprogramming.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.uasmobileprogramming.R;
import com.example.uasmobileprogramming.adapters.MovieAdapter;
import com.example.uasmobileprogramming.models.IMDBResponse;
import com.example.uasmobileprogramming.models.Movie;
import com.example.uasmobileprogramming.utils.APIInterface;
import com.example.uasmobileprogramming.utils.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment{
    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private SearchView searchBar;
    private View v;
    private ArrayList<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        movieList = new ArrayList<>();

        searchBar = v.findViewById(R.id.search_bar);
        rvMovies = v.findViewById(R.id.rv_items);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(new MovieAdapter(movieList, getActivity()));

        APIInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(APIInterface.class);
        Call<IMDBResponse> call = apiInterface.getAllMovies();
        call.enqueue(new Callback<IMDBResponse>() {
            @Override
            public void onResponse(Call<IMDBResponse> call, Response<IMDBResponse> response) {
                if(response.isSuccessful()){
                    IMDBResponse imdbResponse = response.body();
                    assert imdbResponse != null;
                    movieList = imdbResponse.getItems();
                    movieAdapter = new MovieAdapter(movieList, getActivity());
                    rvMovies.setAdapter(movieAdapter);
                }else{
                    Toast.makeText(getActivity(), "Request Error :: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IMDBResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "There is error! :: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        searchBar = v.findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")) filter(newText);
                return true;
            }
        });

        return v;
    }

    private void filter(String text){
        if(movieAdapter == null) return;
        ArrayList<Movie> filteredList = new ArrayList<>();
        if(text.equals("")) filteredList = movieList;
        else {
            for (Movie m : movieList) {
                if (m.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(m);
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(getActivity(), "No Data Found.", Toast.LENGTH_SHORT).show();
            }
        }
        movieAdapter.filterList(filteredList);
    }
}