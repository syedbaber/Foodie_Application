package com.example.foodapp.AdminFoodPanel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Admin_User_Login_Menu;
import com.example.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Admin_Profile_Fragment extends Fragment {

    TextView Name, Email, Phone;
    Button Logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin__profile_, container, false);

        Name= view.findViewById(R.id.User_Name);
        Email= view.findViewById(R.id.User_Email);
        Phone= view.findViewById(R.id.User_Phone);
        Logout= view.findViewById(R.id.btn_logOut);

        //Firebase
        String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference uidReference= FirebaseDatabase.getInstance().getReference().child("Admin").child(UID);

        uidReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fname= snapshot.child("fname").getValue(String.class);
                String lname= snapshot.child("lname").getValue(String.class);
                String email= snapshot.child("email").getValue(String.class);
                String phone= snapshot.child("phone").getValue(String.class);

                //Assigning Values
                Name.setText(fname + " " + lname);
                Email.setText(email);
                Phone.setText(phone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

        // ------ Logout Button
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutFun();
            }
        });


        return view;
    }

    private void LogoutFun() {
        FirebaseAuth.getInstance().signOut();

        Intent intent= new Intent(getContext(), Admin_User_Login_Menu.class);
        startActivity(intent);
    }
}