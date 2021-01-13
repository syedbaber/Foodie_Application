package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Chef_User_SignUp_Menu extends AppCompatActivity {

    Button register_User, register_Chef;
    TextView login_Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef__user__sign_up__menu);

        register_Chef=(Button)findViewById(R.id.btn_chefLogin);
        register_User=(Button)findViewById(R.id.btn_UserLogin);
        login_Link=(TextView)findViewById(R.id.SignupTV);

        register_Chef.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), Chef_Registration.class);
            startActivity(intent);
        });

        register_User.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), User_Registration.class);
            startActivity(intent);
        });

        login_Link.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), User_Login.class);
            startActivity(intent);
            finish();
        });
    }
}