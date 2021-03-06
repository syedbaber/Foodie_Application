package com.example.foodapp.AdminFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodapp.Admin_Registration;
import com.example.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Home_Fragment extends Fragment {

    Button btn_PostDish, btn_AddUser, btn_AddRider, btn_deleteDish;
    TextView ordersCount, dishesCount, userName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_admin_home, null);
        getActivity().setTitle("Home");

        btn_PostDish= view.findViewById(R.id.btn_postDish);
        btn_deleteDish=view.findViewById(R.id.btn_deleteDish);
        btn_AddUser= view.findViewById(R.id.btn_add_Admin);
        btn_AddRider=view.findViewById(R.id.btn_add_Rider);
        ordersCount= view.findViewById(R.id.Orders_Count);
        dishesCount= view.findViewById(R.id.Dishes_Count);
        userName= view.findViewById(R.id.UserName);

        btn_PostDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(view.getContext(), Admin_Post_Dish.class);
                startActivity(intent);
            }
        });

        btn_deleteDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Dishes_List_Fragment()).addToBackStack(null).commit();

            }
        });

        btn_AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(view.getContext(), Admin_Registration.class);
                startActivity(intent);
            }
        });

        btn_AddRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), Rider_Registration.class);
                startActivity(intent);
            }
        });

        //Get Order Count from Firebase
        DatabaseReference db_OrderRequest= FirebaseDatabase.getInstance().getReference().child("Order_Requests");

        db_OrderRequest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long ActiveOrders= snapshot.getChildrenCount();

                //Assigning to orderCount TextView
                ordersCount.setText(String.valueOf(ActiveOrders));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get Dishes Count from Firebase
        DatabaseReference db_Users= FirebaseDatabase.getInstance().getReference().child("FoodDetails");
        db_Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long ActiveOrders= snapshot.getChildrenCount();

                //Assigning to dishesCount TextView
                dishesCount.setText(String.valueOf(ActiveOrders));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Get Admin Name from Firebase
        String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db_UserName= FirebaseDatabase.getInstance().getReference().child("Admin").child(UID);

        db_UserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fname= snapshot.child("fname").getValue(String.class);
                String lname= snapshot.child("lname").getValue(String.class);

                //Assigning to TextView
                userName.setText(fname+" "+lname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;

    }
}
