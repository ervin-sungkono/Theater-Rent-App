package com.example.uasmobileprogramming.models;

public class Theater {
    private TheaterInfo theaterInfo;
    private String cinema;
    private String rentDuration;
    private int price;
    private String date;
    private Movie movie;

    public Theater(){

    }

    public Theater(String name, String imageUrl, String cinema, String date, String rentDuration, int price, Movie movie) {
        this.theaterInfo = new TheaterInfo(name, imageUrl);
        this.cinema = cinema;
        this.rentDuration = rentDuration;
        this.price = price;
        this.date = date;
        this.movie = movie;
    }

    public TheaterInfo getTheaterInfo() {
        return theaterInfo;
    }

    public void setTheaterInfo(TheaterInfo theaterInfo) {
        this.theaterInfo = theaterInfo;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRentDuration() {
        return rentDuration;
    }

    public void setRentDuration(String rentDuration) {
        this.rentDuration = rentDuration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }


}
