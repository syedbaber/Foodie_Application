package com.example.foodapp.customerFoodPanel;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Customer_Home_Fragment extends Fragment {

    RecyclerView rcv;
    Customer_Home_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_customer__home_,null);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Home</font>"));


        rcv= v.findViewById(R.id.rclView);
       rcv.setLayoutManager(new LinearLayoutManager(getContext()));

       DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("FoodDetails");

        FirebaseRecyclerOptions<DishDetailsModel> options =
                new FirebaseRecyclerOptions.Builder<DishDetailsModel>()
                        .setQuery(query, DishDetailsModel.class)
                        .build();

        adapter= new Customer_Home_Adapter(options);
        rcv.setAdapter(adapter);

        return v;
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