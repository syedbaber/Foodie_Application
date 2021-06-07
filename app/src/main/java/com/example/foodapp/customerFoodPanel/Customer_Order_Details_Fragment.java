package com.example.foodapp.customerFoodPanel;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodapp.AdminFoodPanel.Admin_Order_Tracking;
import com.example.foodapp.R;


public class Customer_Order_Details_Fragment extends Fragment {
    Button btn_trackOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer__order__details_, container, false);

        btn_trackOrder=view.findViewById(R.id.btn_trackOrder);


        btn_trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                Intent intent= new Intent(v.getContext(), Track_Order.class);
                activity.startActivity(intent);
            }
        });



        return view;
    }
}