package com.example.foodapp.AdminFoodPanel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodapp.Model.FoodList_Model;
import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Admin_Order_Food_Details_Fragment extends Fragment {

    String Order_key;
    TextView orderID, customerName, orderStatus, Address, Total;
    RecyclerView recyclerView;
    Admin_FoodList_Adapter adapter;


    public Admin_Order_Food_Details_Fragment() {
    }

    public Admin_Order_Food_Details_Fragment(String key){
        this.Order_key= key;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin__order__food__details_, container, false);

        orderID= view.findViewById(R.id.order_ID);
        customerName= view.findViewById(R.id.customerName);
        orderStatus= view.findViewById(R.id.order_Status);
        Address= view.findViewById(R.id.order_Address);
        Total= view.findViewById(R.id.total_Price);

        recyclerView= view.findViewById(R.id.Rec_foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Firebase
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= database.getReference().child("Order_Requests").child(Order_key);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= snapshot.child("name").getValue().toString();
                String status= snapshot.child("status").getValue().toString();
                String total_price= snapshot.child("total_price").getValue().toString();
                String address= snapshot.child("address").getValue().toString();

                //Assigning Value
                orderID.setText("ID# "+Order_key);
                customerName.setText(name);
                Address.setText(address);
                orderStatus.setText(convertCodeToStatus(status));
                Total.setText("Total: Rs."+total_price);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Recyclerview Activities
        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("Order_Requests").child(Order_key).child("foods");

        FirebaseRecyclerOptions<FoodList_Model> options =
                new FirebaseRecyclerOptions.Builder<FoodList_Model >()
                        .setQuery(query, FoodList_Model.class)
                        .build();

        adapter= new Admin_FoodList_Adapter(options);
        recyclerView.setAdapter(adapter);


        return view;
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