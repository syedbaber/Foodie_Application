package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button signin_Email;
    Button signin_Phone;
    Button sign_Up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sign_Up= (Button)findViewById(R.id.btn_signup);
        signin_Email=(Button)findViewById(R.id.btn_signinwithEmail);

        sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainMenu.this, User_Registration.class);
                startActivity(intent);
            }
        });

        signin_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Admin_User_Login_Menu.class);
                startActivity(intent);
            }
        });

    }
}