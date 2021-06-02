package com.example.foodapp.Model;

//import com.example.foodapp.Model.Order;
import com.example.foodapp.customerFoodPanel.SQL_DB_Model;

import java.util.List;

public class Request_Order_Model {

    private String Name;
    private String Phone;
    private String UID;
    private String Total_price;
    private String status;
    private String address;
    private String SelfPickup;
    private List<SQL_DB_Model> foods;

    public Request_Order_Model() {
    }

    public Request_Order_Model(String name, String phone, String UID, String total_price, String address, String selfPickup, List<SQL_DB_Model> foods) {
        Name = name;
        Phone = phone;
        this.UID = UID;
        Total_price = total_price;
        this.foods = foods;
        this.status="0";  // 0 : Placed Order,  1 : Order Shipping, 2 : Order Shipped..
        this.address= address;
        this.SelfPickup=selfPickup;
    }

    public String getSelfPickup() {
        return SelfPickup;
    }

    public void setSelfPickup(String selfPickup) {
        SelfPickup = selfPickup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTotal_price() {
        return Total_price;
    }

    public void setTotal_price(String total_price) {
        Total_price = total_price;
    }

    public List<SQL_DB_Model> getFoods() {
        return foods;
    }

    public void setFoods(List<SQL_DB_Model> foods) {
        this.foods = foods;
    }
}
