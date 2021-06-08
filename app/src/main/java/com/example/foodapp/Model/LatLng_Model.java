package com.example.foodapp.Model;

public class LatLng_Model {
    private double customer_Latitude;
    private double customer_Longitude;

    public LatLng_Model() {
    }

    public LatLng_Model(double customer_Latitude, double customer_Longitude) {
        this.customer_Latitude = customer_Latitude;
        this.customer_Longitude = customer_Longitude;

    }

    public double getCustomer_Latitude() {
        return customer_Latitude;
    }

    public void setCustomer_Latitude(double customer_Latitude) {
        this.customer_Latitude = customer_Latitude;
    }

    public double getCustomer_Longitude() {
        return customer_Longitude;
    }

    public void setCustomer_Longitude(double customer_Longitude) {
        this.customer_Longitude = customer_Longitude;
    }
}
