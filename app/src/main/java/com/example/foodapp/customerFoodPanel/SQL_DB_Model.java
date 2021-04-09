package com.example.foodapp.customerFoodPanel;

public class SQL_DB_Model {
    String dishName, price, imageURL, id, quantity;

    public SQL_DB_Model() {
    }


    public SQL_DB_Model(String id, String dishName, String price, String imageURL, String quantity) {
        this.id= id;
        this.dishName = dishName;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
