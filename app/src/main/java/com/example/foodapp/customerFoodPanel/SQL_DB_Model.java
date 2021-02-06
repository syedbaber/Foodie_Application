package com.example.foodapp.customerFoodPanel;

public class SQL_DB_Model {
    String dishName, price, imageURL;

    public SQL_DB_Model() {
    }

    public SQL_DB_Model(String dishName, String price, String imageURL) {
        this.dishName = dishName;
      //  this.description = description;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

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
}
