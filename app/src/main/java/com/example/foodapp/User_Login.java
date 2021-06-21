package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_Login extends AppCompatActivity {

    private EditText emailId;
    private EditText password;
    private Button login;
    private TextView signUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login2);

        getSupportActionBar().hide();

        firebaseAuth=firebaseAuth.getInstance();
        emailId=(EditText)findViewById(R.id.email_Login);
        password=(EditText)findViewById(R.id.password_Login);
        login= (Button)findViewById(R.id.btn_login);
        signUp= (TextView) findViewById(R.id.SignupTV);
        progressDialog= new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginfun();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                finish();
            }
        });
    }

   private void Loginfun(){
       String Email= emailId.getText().toString();
       String Password= password.getText().toString();

       if(TextUtils.isEmpty(Email)){
           emailId.setError("Enter your Email");
           return;
       }
       else if(TextUtils.isEmpty(Password)){
           emailId.setError("Enter your password");
           return;
       }

       progressDialog.setMessage("Please wait...");
       progressDialog.show();
       progressDialog.setCanceledOnTouchOutside(false);

       firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Toast.makeText(User_Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                   Intent intent= new Intent(User_Login.this, User_Home.class);
                   startActivity(intent);
                   finish();
               }
               else {
                   Toast.makeText(User_Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
               }
               progressDialog.dismiss();
           }
       });

   }
}