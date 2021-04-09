package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodapp.AdminFoodPanel.Admin_Home_Fragment;
import com.example.foodapp.AdminFoodPanel.Admin_Orders_Fragment;
import com.example.foodapp.AdminFoodPanel.Admin_PendingOrders_Fragment;
import com.example.foodapp.AdminFoodPanel.Admin_Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=findViewById(R.id.chef_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.chefHome:
                fragment= new Admin_Home_Fragment();
                break;
            case R.id.pendingOrders:
                fragment= new Admin_PendingOrders_Fragment();
                break;

            case R.id.orders:
                fragment= new Admin_Orders_Fragment();
                break;

            case R.id.chefProfile:
                fragment= new Admin_Profile_Fragment();
                break;
        }
        return loadChefFragment(fragment);
    }

    private boolean loadChefFragment(Fragment fragment) {
        if(fragment!= null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
        }
        return true;
    }
}