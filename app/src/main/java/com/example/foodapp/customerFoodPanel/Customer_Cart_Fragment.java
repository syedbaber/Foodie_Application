package com.example.foodapp.customerFoodPanel;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.R;

import java.util.ArrayList;

public class Customer_Cart_Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<SQL_DB_Model> dataHolder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer__cart_, container, false);

        recyclerView=view.findViewById(R.id.cart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Cursor cursor= new DB_Manager(getContext()).getDbData();

        while (cursor.moveToNext()){
            SQL_DB_Model obj= new SQL_DB_Model(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            dataHolder.add(obj);
        }

        Customer_Cart_Adapter adapter= new Customer_Cart_Adapter(dataHolder);
        recyclerView.setAdapter(adapter);

        return view;
    }
}