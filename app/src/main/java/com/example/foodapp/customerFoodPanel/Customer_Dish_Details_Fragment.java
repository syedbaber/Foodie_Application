package com.example.foodapp.customerFoodPanel;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;

public class Customer_Dish_Details_Fragment extends Fragment {

    String name, price, description, imageURL;
    TextView nameHolder, priceHolder, descriptionHolder, quantity;
    ImageView imageHolder;
    ImageButton btn_cart,btn_plus, btn_minus;

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
        quantity= view.findViewById(R.id.DQuantity);
        imageHolder=view.findViewById(R.id.ImageHolder);
        btn_cart= view.findViewById(R.id.btn_addCart);
        btn_plus= view.findViewById(R.id.btn_plus1);
        btn_minus= view.findViewById(R.id.btn_minus1);

        nameHolder.setText(name);
        priceHolder.setText("Rs. "+ price);
        descriptionHolder.setText(description);
        Glide.with(getContext()).load(imageURL).into(imageHolder);


        //Quantity Increase Button
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q= Integer.parseInt(quantity.getText().toString());
                q = q + 1;
                quantity.setText(String.valueOf(q));
            }
        });

        //Quantity Decrease Button
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(quantity.getText().toString()) !=1 ){
                    int q= Integer.parseInt(quantity.getText().toString());
                    q = q - 1;
                    quantity.setText(String.valueOf(q));
                }
            }
        });


        //Add to Cart Button
        btn_cart.setOnClickListener(v -> {
            String result= new DB_Manager(getContext()).insertDbData(name,price,imageURL, quantity.getText().toString());

            btn_cart.setImageResource(R.drawable.ic_cart_after_addition_24);
            btn_cart.setEnabled(false);
        });

        return view;
    }

}