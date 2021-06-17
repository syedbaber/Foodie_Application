package com.example.foodapp.customerFoodPanel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Customer_Orders_Fragment extends Fragment {

    RecyclerView recyclerView;
    Customer_Order_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer__orders_, container, false);
        getActivity().setTitle("Orders");

        recyclerView= view.findViewById(R.id.Order_List);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        //Firebase
        String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query= FirebaseDatabase.getInstance().getReference().child("Order_Requests").orderByChild("uid").equalTo(UID);

        FirebaseRecyclerOptions<Request_Order_Model> options =
                new FirebaseRecyclerOptions.Builder<Request_Order_Model>()
                        .setQuery(query, Request_Order_Model.class)
                        .build();

        adapter= new Customer_Order_Adapter(options);
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