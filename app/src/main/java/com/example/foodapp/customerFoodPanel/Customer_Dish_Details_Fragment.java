package com.example.foodapp.customerFoodPanel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;

public class Customer_Dish_Details_Fragment extends Fragment {

    String name, price, description, imageURL;
    TextView nameHolder, priceHolder, descriptionHolder;
    ImageView imageHolder;

    public Customer_Dish_Details_Fragment() {
    }

    public Customer_Dish_Details_Fragment(String name, String price, String description, String imageURL) {
        this.name=name;
        this.price=price;
        this.description=description;
        this.imageURL=imageURL;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_customer__dish__details, container, false);
        nameHolder= view.findViewById(R.id.NameHolder);
        priceHolder= view.findViewById(R.id.PriceHolder);
        descriptionHolder=view.findViewById(R.id.descriptionHolder);
        imageHolder=view.findViewById(R.id.ImageHolder);

        nameHolder.setText(name);
        priceHolder.setText(price);
        descriptionHolder.setText(description);
        Glide.with(getContext()).load(imageURL).into(imageHolder);
        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Customer_Home_Fragment()).addToBackStack(null).commit();
    }
}