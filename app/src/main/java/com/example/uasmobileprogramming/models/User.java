package com.example.uasmobileprogramming.models;

import java.util.ArrayList;

public class User {
    private String username, email;
    private ArrayList<Theater> theaterList;

    public User(String username, String email, ArrayList<Theater> theaterList) {
        this.username = username;
        this.email = email;
        this.theaterList = theaterList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Theater> getTheaterList() {
        return theaterList;
    }

    public void setTheaterList(ArrayList<Theater> theaterList) {
        this.theaterList = theaterList;
    }
}
