package com.example.foodapp.Model;

public class FoodList_Model {
    private String dishName, ID, ImageURL, Price, Quantity;

    public FoodList_Model() {
    }

    public FoodList_Model(String dishName, String ID, String imageURL, String price, String quantity) {
        this.dishName = dishName;
        this.ID = ID;
        ImageURL = imageURL;
        Price = price;
        Quantity = quantity;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
