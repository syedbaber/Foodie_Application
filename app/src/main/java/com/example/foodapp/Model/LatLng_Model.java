package com.example.foodapp.Model;

public class LatLng_Model {
    private double Latitude;
    private double Longitude;

    public LatLng_Model() {
    }

    public LatLng_Model(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
