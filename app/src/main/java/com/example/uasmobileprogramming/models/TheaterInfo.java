package com.example.uasmobileprogramming.models;

public class TheaterInfo {
    private String name, imageUrl;

    public TheaterInfo(){

    }

    public TheaterInfo(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return name;
    }
}
