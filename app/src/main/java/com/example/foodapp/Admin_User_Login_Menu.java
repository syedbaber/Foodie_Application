package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Admin_User_Login_Menu extends AppCompatActivity {

    Button adminLogin, userLogin;
    TextView signupTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__user__login__menu);

        adminLogin=(Button)findViewById(R.id.btn_chefLogin);
        userLogin=(Button)findViewById(R.id.btn_UserLogin);
        signupTV=(TextView)findViewById(R.id.SignupTV);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Admin_Login.class);
                startActivity(intent);
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent);
            }
        });

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), User_Registration.class);
                startActivity(intent);
            }
        });
    }
}