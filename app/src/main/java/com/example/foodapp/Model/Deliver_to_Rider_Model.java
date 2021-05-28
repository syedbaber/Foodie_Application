package com.example.foodapp.Model;

public class Deliver_to_Rider_Model {
    private String orderID;
    private String Rider_ID;

    public Deliver_to_Rider_Model() { }

    public Deliver_to_Rider_Model(String orderID, String rider_ID) {
        this.orderID = orderID;
        Rider_ID = rider_ID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getRider_ID() {
        return Rider_ID;
    }

    public void setRider_ID(String rider_ID) {
        Rider_ID = rider_ID;
    }
}
