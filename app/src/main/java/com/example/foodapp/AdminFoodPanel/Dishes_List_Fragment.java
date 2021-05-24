package com.example.foodapp.AdminFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.FoodList_Model;
import com.example.foodapp.R;
import com.example.foodapp.customerFoodPanel.DishDetailsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dishes_List_Fragment extends Fragment {

    RecyclerView recyclerView;
    Admin_Dishes_List_Adapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dishes__list_, container, false);

        recyclerView= view.findViewById(R.id.Rec_dishesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        DatabaseReference query= FirebaseDatabase.getInstance().getReference().child("FoodDetails");

        FirebaseRecyclerOptions<DishDetailsModel> options =
                new FirebaseRecyclerOptions.Builder<DishDetailsModel>()
                        .setQuery(query, DishDetailsModel.class)
                        .build();

        adapter= new Admin_Dishes_List_Adapter(options);
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