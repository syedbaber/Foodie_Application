package com.example.foodapp.customerFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Customer_Home_Adapter extends FirebaseRecyclerAdapter<DishDetailsModel, Customer_Home_Adapter.myViewholder> {


    public Customer_Home_Adapter(@NonNull FirebaseRecyclerOptions<DishDetailsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder myViewholder, int i, @NonNull DishDetailsModel dishDetailsModel) {
        myViewholder.dName.setText(dishDetailsModel.getDishName());
        myViewholder.dPrice.setText("Rs. "+dishDetailsModel.getPrice());
        Glide.with(myViewholder.img.getContext()).load(dishDetailsModel.getImageURL()).into(myViewholder.img);

            myViewholder.cardView.setOnClickListener(v -> {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Customer_Dish_Details_Fragment(dishDetailsModel.getDishName(),dishDetailsModel.getPrice(),dishDetailsModel.getDescription(),dishDetailsModel.getImageURL())).addToBackStack(null).commit();
            });
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_menu_dish,parent,false);
        return new myViewholder(view);
    }

    public class myViewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView dName, dPrice;
        CardView cardView;

        public myViewholder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.menu_image);
            dName= itemView.findViewById(R.id.dishName);
            dPrice= itemView.findViewById(R.id.dishPrice);
            cardView= itemView.findViewById(R.id.singleRowCard);

        }
    }
}
