package com.example.foodapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.foodapp.customerFoodPanel.Customer_Profile_Fragment;
import com.example.foodapp.customerFoodPanel.Customer_Cart_Fragment;
import com.example.foodapp.customerFoodPanel.Customer_Home_Fragment;
import com.example.foodapp.customerFoodPanel.Customer_Orders_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class User_Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Home</font>"));

        navigationView=findViewById(R.id.customer_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Customer_Home_Fragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.customerHome:
                fragment= new Customer_Home_Fragment();
                break;

            case R.id.cutomerCart:
                fragment= new Customer_Cart_Fragment();
                break;

            case R.id.customerOrders:
                fragment= new Customer_Orders_Fragment();
                break;

            case R.id.customerProfile:
                fragment= new Customer_Profile_Fragment();
                break;
        }
        return loadCustomerFragment(fragment);
    }

    private boolean loadCustomerFragment(Fragment fragment) {

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
        return true;
    }
}