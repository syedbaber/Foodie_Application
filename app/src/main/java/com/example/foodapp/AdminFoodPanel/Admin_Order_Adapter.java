package com.example.foodapp.AdminFoodPanel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.example.foodapp.customerFoodPanel.Customer_Dish_Details_Fragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;


public class Admin_Order_Adapter extends FirebaseRecyclerAdapter<Request_Order_Model, Admin_Order_Adapter.myViewHolder> {

    MaterialSpinner spinner;
    DatabaseReference dbReference= FirebaseDatabase.getInstance().getReference().child("Order_Requests");

    public Admin_Order_Adapter(@NonNull FirebaseRecyclerOptions<Request_Order_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position, @NonNull Request_Order_Model request_order_model) {
        myViewHolder.order_ID.setText("ID# "+getRef(position).getKey());
        myViewHolder.order_Status.setText(convertCodeToStatus(request_order_model.getStatus()));
        myViewHolder.order_Address.setText(request_order_model.getAddress());
        myViewHolder.customerName.setText(request_order_model.getName());


        myViewHolder.btn_editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUpdateDialog(v.getContext(), getRef(position).getKey(),getItem(position));
            }
        });

        myViewHolder.btn_deliverOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Riders_List()).addToBackStack(null).commit();

            }
        });

        myViewHolder.btn_deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbReference.child(getRef(position).getKey()).removeValue();
            }
        });

        myViewHolder.btn_Order_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key= getRef(position).getKey();
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Admin_Order_Food_Details_Fragment(key)).addToBackStack(null).commit();

            }
        });

        myViewHolder.btn_OrderLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String  clientAddress= request_order_model.getAddress(); //For sending to Admin_Track_Order class.

                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                Intent intent= new Intent(v.getContext(), Admin_Order_Tracking.class);

                intent.putExtra("clientAddress", clientAddress);

                activity.startActivity(intent);
            }
        });


    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_orders_single_row,parent,false);

        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView order_ID, order_Status, order_Address, customerName;
        Button btn_editStatus, btn_deleteOrder, btn_Order_Details, btn_OrderLocation, btn_deliverOrder;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            order_ID= itemView.findViewById(R.id.order_ID);
            order_Status= itemView.findViewById(R.id.order_Status);
            order_Address= itemView.findViewById(R.id.order_Address);
            customerName= itemView.findViewById(R.id.customerName);
            btn_editStatus= itemView.findViewById(R.id.btn_editStatus);
            btn_deleteOrder= itemView.findViewById(R.id.btn_deleteOrder);
            btn_Order_Details= itemView.findViewById(R.id.btn_orderDetials);
            btn_OrderLocation= itemView.findViewById(R.id.btn_OrderLocation);
            btn_deliverOrder= itemView.findViewById(R.id.btn_deliverOrder);
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


    // Edit Order Status DialogBox
    private void showUpdateDialog(Context context, String key, Request_Order_Model item) {

        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
        alertDialog.setTitle("Update Order Status");
        alertDialog.setMessage("Please choose the status.");

        LayoutInflater inflater= LayoutInflater.from(context);
        final View view= inflater.inflate(R.layout.admin_update_order_spinner_layout,null);

        spinner= view.findViewById(R.id.status_Spinner);
        spinner.setItems("Order Placed", "On My Way", "Delivered");

        alertDialog.setView(view);

        final String localKey= key;

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                dbReference.child(localKey).setValue(item);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }


}
