package com.example.eartquakenews.model;

import android.net.Uri;

import java.net.URL;

public class Earthquake {
    private double longitude;
    private double latitude;
    private String placeName;
    private String url;

    public Earthquake() {
    }

    public Earthquake(double latitude, double longitude, String placeName, String url) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.placeName = placeName;
        this.url = url;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
