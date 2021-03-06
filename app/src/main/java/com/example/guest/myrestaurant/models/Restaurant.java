package com.example.guest.myrestaurant.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Guest on 5/27/17.
 */

@Parcel
public class Restaurant {
    private String name;
    private String phone;
    private String website;
    private double rating;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private ArrayList<String> address = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    public Restaurant() { }

    public Restaurant(String name, String phone, String website, double rating, String imageUrl, double latitude, double longitude, ArrayList<String> address, ArrayList<String> categories) {
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
