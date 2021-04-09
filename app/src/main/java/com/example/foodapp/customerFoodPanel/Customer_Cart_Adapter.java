package com.example.foodapp.customerFoodPanel;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Customer_Cart_Adapter extends RecyclerView.Adapter<Customer_Cart_Adapter.viewHolder> {

    ArrayList<SQL_DB_Model> dataHolder;
  //  public int adapterPosition;

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
        int id= Integer.parseInt(dataHolder.get(position).getId());
        holder.itemView.setTag(id);

        holder.dishName.setText(dataHolder.get(position).getDishName());
        holder.dishPrice.setText("Price: Rs." + dataHolder.get(position).getPrice());
        holder.dishQuantity.setText(dataHolder.get(position).getQuantity());
        Glide.with(holder.dishImage.getContext()).load(dataHolder.get(position).getImageURL()).into(holder.dishImage);

 //       adapterPosition= position;
    }


    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView dishName, dishPrice, dishQuantity;
        ImageView dishImage;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dishName= itemView.findViewById(R.id.dishName);
            dishPrice= itemView.findViewById(R.id.dishPrice);
            dishQuantity= itemView.findViewById(R.id.DishQuantity);
            dishImage= itemView.findViewById(R.id.dishImage);
            cardView= itemView.findViewById(R.id.cart_order_cardView);

        }
    }

//    private void removeAt(int position) {
//        dataHolder.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, dataHolder.size());
//    }
}
