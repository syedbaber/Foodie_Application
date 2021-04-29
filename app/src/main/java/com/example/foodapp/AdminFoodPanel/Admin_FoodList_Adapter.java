package com.example.foodapp.AdminFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Model.FoodList_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Admin_FoodList_Adapter extends FirebaseRecyclerAdapter<FoodList_Model, Admin_FoodList_Adapter.myViewHolder> {


    public Admin_FoodList_Adapter(@NonNull FirebaseRecyclerOptions<FoodList_Model> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull FoodList_Model foodList_model) {
        myViewHolder.foodName.setText(foodList_model.getDishName());
        myViewHolder.foodQuantity.setText("Quantity: "+foodList_model.getQuantity());
        Glide.with(myViewHolder.foodImage.getContext()).load(foodList_model.getImageURL()).into(myViewHolder.foodImage);
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_food_list_single_row,parent,false);

        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodQuantity;
        ImageView foodImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName= itemView.findViewById(R.id.food_name);
            foodQuantity= itemView.findViewById(R.id.food_quantity);
            foodImage= itemView.findViewById(R.id.food_image);
        }
    }
}
