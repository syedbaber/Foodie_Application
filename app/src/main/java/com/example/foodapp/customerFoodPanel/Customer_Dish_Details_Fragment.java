package com.example.foodapp.customerFoodPanel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;

public class Customer_Dish_Details_Fragment extends Fragment {

    String name, price, description, imageURL;
    TextView nameHolder, priceHolder, descriptionHolder;
    ImageView imageHolder;
    ImageButton btn_cart;

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
        btn_cart= view.findViewById(R.id.btn_addCart);

        nameHolder.setText(name);
        priceHolder.setText("Rs. "+ price);
        descriptionHolder.setText(description);
        Glide.with(getContext()).load(imageURL).into(imageHolder);

        //Add to Cart Button
        btn_cart.setOnClickListener(v -> {
            String result= new DB_Manager(getContext()).insertDbData(name,price,description,imageURL);
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

            btn_cart.setImageResource(R.drawable.ic_cart_after_addition_24);
            btn_cart.setEnabled(false);
        });

        return view;
    }

}