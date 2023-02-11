package com.example.uasmobileprogramming.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("runtimeMins")
    private String runtimeMins;

    @SerializedName("plot")
    private String plot;

    @SerializedName("contentRating")
    private String contentRating;

    @SerializedName("imDbRating")
    private String imDbRating;

    @SerializedName("imDbRatingCount")
    private String imDbRatingCount;

    @SerializedName("genres")
    private String genres;

    @SerializedName("directors")
    private String directors;

    @SerializedName("stars")
    private String stars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getImDbRatingCount() {
        return imDbRatingCount;
    }

    public void setImDbRatingCount(String imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public int getPrice() {
        return (int) Math.ceil((double)Integer.parseInt(runtimeMins) /60.0) * 200000;
    }

    @Override
    public String toString() {
        return title;
    }
}
