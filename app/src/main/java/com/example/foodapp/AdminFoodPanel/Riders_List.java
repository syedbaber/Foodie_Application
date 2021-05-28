package com.example.foodapp.AdminFoodPanel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.Model.Rider_Registration_Model;
import com.example.foodapp.R;
import com.example.foodapp.customerFoodPanel.DishDetailsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Riders_List extends Fragment {

    RecyclerView recyclerView;
    Riders_List_Adapter adapter;
    String Order_ID;
    String Customer_Name;
    TextView order_Key;

    public Riders_List (){}

    public Riders_List(String order_ID, String Customer_name){

        this.Order_ID= order_ID;
        this.Customer_Name= Customer_name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_riders__list, container, false);

        order_Key= view.findViewById(R.id.order_idd);
        order_Key.setText(Order_ID);

        recyclerView= view.findViewById(R.id.Rec_RidersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       //Firebase
        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("Riders");

        Query query1= FirebaseDatabase.getInstance().getReference("Riders").orderByChild("status").equalTo("0");

        FirebaseRecyclerOptions<Rider_Registration_Model> options =
                new FirebaseRecyclerOptions.Builder<Rider_Registration_Model>()
                        .setQuery(query1, Rider_Registration_Model.class)
                        .build();

        adapter= new Riders_List_Adapter(options, Order_ID, Customer_Name);
        recyclerView.setAdapter(adapter);

        return view;
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