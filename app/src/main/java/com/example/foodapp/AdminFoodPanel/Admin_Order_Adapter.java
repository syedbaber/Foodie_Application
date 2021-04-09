package com.example.foodapp.AdminFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Admin_Order_Adapter extends FirebaseRecyclerAdapter<Request_Order_Model, Admin_Order_Adapter.myViewHolder> {


    public Admin_Order_Adapter(@NonNull FirebaseRecyclerOptions<Request_Order_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position, @NonNull Request_Order_Model request_order_model) {
        myViewHolder.order_ID.setText("ID# "+getRef(position).getKey());
        myViewHolder.order_Status.setText(convertCodeToStatus(request_order_model.getStatus()));
        myViewHolder.order_Address.setText(request_order_model.getAddress());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_orders_single_row,parent,false);

        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView order_ID, order_Status, order_Address;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            order_ID= itemView.findViewById(R.id.order_ID);
            order_Status= itemView.findViewById(R.id.order_Status);
            order_Address= itemView.findViewById(R.id.order_Address);
        }
    }

    private String convertCodeToStatus(String status) {
        String txt_Status = null;
        if(status.equals("0") )
            txt_Status= "Order placed.";

        else if(status.equals("1"))
            txt_Status= "On my way.";

        else if (status.equals("2"))
            txt_Status= "Order delivered";

        return txt_Status;
    }
}
