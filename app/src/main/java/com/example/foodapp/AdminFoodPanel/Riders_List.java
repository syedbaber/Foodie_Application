package com.example.foodapp.AdminFoodPanel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.Model.Rider_Registration_Model;
import com.example.foodapp.R;
import com.example.foodapp.customerFoodPanel.DishDetailsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Riders_List extends Fragment {

    RecyclerView recyclerView;
    Riders_List_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.fragment_riders__list, container, false);

        recyclerView= view.findViewById(R.id.Rec_RidersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       //Firebase
        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("Riders");

        FirebaseRecyclerOptions<Rider_Registration_Model> options =
                new FirebaseRecyclerOptions.Builder<Rider_Registration_Model>()
                        .setQuery(query, Rider_Registration_Model.class)
                        .build();

        adapter= new Riders_List_Adapter(options);
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