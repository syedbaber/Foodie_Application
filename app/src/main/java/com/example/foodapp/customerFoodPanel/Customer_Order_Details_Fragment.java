package com.example.foodapp.customerFoodPanel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.foodapp.AdminFoodPanel.Admin_FoodList_Adapter;
import com.example.foodapp.AdminFoodPanel.Admin_Order_Tracking;
import com.example.foodapp.Model.FoodList_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Customer_Order_Details_Fragment extends Fragment {
    ImageButton btn_trackOrder;
    String Order_ID;
    TextView orderID, customerName, orderStatus, Total, completed_tag, address, alert;
    RecyclerView recyclerView;
    Admin_FoodList_Adapter adapter;
    String pickup;

    public Customer_Order_Details_Fragment(String Order_key){
        this.Order_ID= Order_key;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer__order__details_, container, false);

        btn_trackOrder=view.findViewById(R.id.btn_trackOrder);
        orderID= view.findViewById(R.id.order_ID);
        customerName= view.findViewById(R.id.customerName);
        orderStatus= view.findViewById(R.id.order_Status);
        Total= view.findViewById(R.id.total_Price);
        completed_tag= view.findViewById(R.id.Pickup_Completed_tag);
        address= view.findViewById(R.id.deliveryAddress);
        alert=view.findViewById(R.id.DeliveryMessage);


        recyclerView= view.findViewById(R.id.Rec_foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



//Firebase
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= database.getReference().child("Order_Requests").child(Order_ID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= snapshot.child("name").getValue().toString();
                String status= snapshot.child("status").getValue().toString();
                String total_price= snapshot.child("total_price").getValue().toString();
                String deliveryAddress= snapshot.child("address").getValue().toString();
                String SelfPickup= snapshot.child("selfPickup").getValue().toString();


                //Assigning Value
                orderID.setText("ID# "+Order_ID);
                customerName.setText(name);
                orderStatus.setText(convertCodeToStatus(status));
                address.setText(deliveryAddress);
                Total.setText("Total: Rs."+total_price);
                pickup= SelfPickup;

                //Setting Completed tag and Location button visibility
                if(status.equals("1") && pickup.equals("no")){
                    btn_trackOrder.setVisibility(View.VISIBLE);
                }

                if(status.equals("2")){
                    completed_tag.setVisibility(View.VISIBLE);
                }
                else {
                    completed_tag.setVisibility(View.GONE);
                }
                if(pickup.equals("no") && status.equals("1")){
                    orderStatus.setText("On My Way!");
                }
                else if(pickup.equals("yes") && status.equals("1")){
                    orderStatus.setText("Ready to deliver!");
                    alert.setVisibility(View.VISIBLE);
                }
                else {
                    alert.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Recyclerview Activities
        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("Order_Requests").child(Order_ID).child("foods");

        FirebaseRecyclerOptions<FoodList_Model> options =
                new FirebaseRecyclerOptions.Builder<FoodList_Model >()
                        .setQuery(query, FoodList_Model.class)
                        .build();

        adapter= new Admin_FoodList_Adapter(options);
        recyclerView.setAdapter(adapter);

        //Buttons Click Listeners
        btn_trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                Intent intent= new Intent(v.getContext(), Track_Order.class);
                intent.putExtra("Order_key",Order_ID);
                activity.startActivity(intent);
            }
        });


        return view;
    }

    private String convertCodeToStatus(String status) {
        String txt_Status = null;
        if(status.equals("0") )
            txt_Status= "Order Placed";

        else if(status.equals("1"))
            txt_Status= "Ready to deliver";

        else if (status.equals("2"))
            txt_Status= "Delivered";

        return txt_Status;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}