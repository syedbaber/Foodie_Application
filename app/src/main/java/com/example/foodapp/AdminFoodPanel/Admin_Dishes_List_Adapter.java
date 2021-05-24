package com.example.foodapp.AdminFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Model.FoodList_Model;
import com.example.foodapp.R;
import com.example.foodapp.customerFoodPanel.DishDetailsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Admin_Dishes_List_Adapter extends FirebaseRecyclerAdapter<DishDetailsModel, Admin_Dishes_List_Adapter.myViewHolder> {

    DatabaseReference dbReference= FirebaseDatabase.getInstance().getReference().child("FoodDetails");

    public Admin_Dishes_List_Adapter(@NonNull FirebaseRecyclerOptions<DishDetailsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull DishDetailsModel dishDetailsModel) {
        myViewHolder.foodName.setText(dishDetailsModel.getDishName());
        myViewHolder.foodPrice.setText("Price: "+dishDetailsModel.getPrice());
        Glide.with(myViewHolder.foodImage.getContext()).load(dishDetailsModel.getImageURL()).into(myViewHolder.foodImage);

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.btn_DeleteFood.setVisibility(v.VISIBLE);
            }
        });

        myViewHolder.btn_DeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbReference.child(getRef(i).getKey()).removeValue();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_dishes_list_single_row,parent,false);

        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice;
        CardView cardView;
        ImageView foodImage;
        Button btn_DeleteFood;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
          foodName= itemView.findViewById(R.id.Food_name);
          foodPrice= itemView.findViewById(R.id.food_price);
          foodImage= itemView.findViewById(R.id.food_image);
          cardView= itemView.findViewById(R.id.admin_dishesList_cardView);
          btn_DeleteFood= itemView.findViewById(R.id.btn_deleteDish);
        }
    }
}
