package com.example.foodapp.customerFoodPanel;

public class DishDetailsModel {
    String ChefId, Description, ImageURL, Price, Quantity, RandomUID, DishName;

    public DishDetailsModel() {
    }

    public DishDetailsModel(String chefId, String description, String imageURL, String price, String quantity, String randomUID, String dishName) {
        ChefId = chefId;
        Description = description;
        ImageURL = imageURL;
        Price = price;
        Quantity = quantity;
        RandomUID = randomUID;
        this.DishName = dishName;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        this.DishName = dishName;
    }
}
