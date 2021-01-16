package com.example.foodapp.customerFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;

import java.util.ArrayList;

public class Customer_Cart_Adapter extends RecyclerView.Adapter<Customer_Cart_Adapter.viewHolder> {

    ArrayList<SQL_DB_Model> dataHolder;

    public Customer_Cart_Adapter(ArrayList<SQL_DB_Model> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_single_row_cardview,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.dishName.setText(dataHolder.get(position).getDishName());
        holder.dishPrice.setText("Price: Rs." + dataHolder.get(position).getPrice());
        Glide.with(holder.dishImage.getContext()).load(dataHolder.get(position).getImageURL()).into(holder.dishImage);

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView dishName, dishPrice;
        ImageView dishImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dishName= itemView.findViewById(R.id.dishName);
            dishPrice= itemView.findViewById(R.id.dishPrice);
            dishImage= itemView.findViewById(R.id.dishImage);
        }
    }
}
