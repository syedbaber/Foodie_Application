package com.example.foodapp.customerFoodPanel;

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

public class Customer_Cart_Adapter extends RecyclerView.Adapter<Customer_Cart_Adapter.viewHolder> {

    ArrayList<SQL_DB_Model> dataHolder;
    public int adapterPosition;

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

        adapterPosition= position;

        holder.cardView.setOnClickListener(v -> {
                 holder.buttonLayout.setVisibility(View.VISIBLE);
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q= Integer.parseInt(holder.Quantity.getText().toString());
                q = q + 1;
                holder.Quantity.setText(String.valueOf(q));
            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.Quantity.getText().toString()) !=1 ){
                    int q= Integer.parseInt(holder.Quantity.getText().toString());
                    q = q - 1;
                    holder.Quantity.setText(String.valueOf(q));
                }
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position !=1){
                    removeAt(adapterPosition-1);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView dishName, dishPrice, Quantity;
        ImageView dishImage;
        ImageButton btn_plus, btn_minus, btn_delete;
        CardView cardView;
        LinearLayout buttonLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dishName= itemView.findViewById(R.id.dishName);
            dishPrice= itemView.findViewById(R.id.dishPrice);
            Quantity= itemView.findViewById(R.id.Quantity);
            dishImage= itemView.findViewById(R.id.dishImage);
            cardView= itemView.findViewById(R.id.cart_order_cardView);
            buttonLayout= itemView.findViewById(R.id.cart_update_buttons);

            btn_plus= itemView.findViewById(R.id.btn_plus);
            btn_minus= itemView.findViewById(R.id.btn_minus);
            btn_delete= itemView.findViewById(R.id.btn_delete);
        }
    }

    private void removeAt(int position) {
        dataHolder.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataHolder.size());
    }
}
