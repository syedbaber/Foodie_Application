package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    Button signin_Email;
    Button signin_Phone;
    Button sign_Up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().hide();

        sign_Up= (Button)findViewById(R.id.btn_signup);
        signin_Email=(Button)findViewById(R.id.btn_signinwithEmail);
        signin_Phone=findViewById(R.id.btn_signinwithPhone);

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
                Intent intent= new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent);
            }
        });

        signin_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Coming Soon....!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}