package com.example.foodapp.AdminFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.example.foodapp.AdminFoodPanel.Admin_Order_Adapter;
import com.example.foodapp.AdminFoodPanel.Admin_Order_Adapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Orders_Fragment extends Fragment {

    RecyclerView recyclerView;
    Admin_Order_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_admin_orders, null);
        getActivity().setTitle("New Orders");

        recyclerView= view.findViewById(R.id.Admin_Order_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Firebase
        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("Order_Requests");

        FirebaseRecyclerOptions<Request_Order_Model> options =
                new FirebaseRecyclerOptions.Builder<Request_Order_Model>()
                        .setQuery(query, Request_Order_Model.class)
                        .build();

        adapter= new Admin_Order_Adapter(options);
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
