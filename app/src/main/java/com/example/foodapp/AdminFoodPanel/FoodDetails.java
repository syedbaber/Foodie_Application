package com.example.foodapp.AdminFoodPanel;

public class FoodDetails {
     public String DishName, Description, Quantity, Price, ImageURL, RandomUID, ChefId;

    public FoodDetails(String dishes, String description, String quantity, String price, String imageURL, String randomUID, String chefId) {
        DishName = dishes;
        Description = description;
        Quantity = quantity;
        Price = price;
        ImageURL = imageURL;
        RandomUID = randomUID;
        ChefId = chefId;
    }
}
