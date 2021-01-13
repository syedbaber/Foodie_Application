package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodapp.chefFoodPanel.Chef_Home_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chef_Registration extends AppCompatActivity {

    private TextInputLayout txt_fname, txt_lname, txt_email, txt_password, txt_confpassword, txt_phone;
    private Button btn_register;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef__registration);

        firebaseAuth=firebaseAuth.getInstance();

        txt_fname= (TextInputLayout) findViewById(R.id.first_name);
        txt_lname= (TextInputLayout)findViewById(R.id.last_name);
        txt_email= (TextInputLayout)findViewById(R.id.email);
        txt_password= (TextInputLayout)findViewById(R.id.password);
        txt_confpassword= (TextInputLayout)findViewById(R.id.confirm_password);
        txt_phone= (TextInputLayout)findViewById(R.id.phone_number);
        btn_register= (Button) findViewById(R.id.btn_register);
        progressDialog=new ProgressDialog(this);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                reference = FirebaseDatabase.getInstance().getReference("Chef").child(UserId);



                //Get all the values
                String First_name= txt_fname.getEditText().getText().toString();
                String Last_name= txt_lname.getEditText().getText().toString();
                String Email= txt_email.getEditText().getText().toString();
                String Pwd= txt_password.getEditText().getText().toString();
                String confPwd= txt_confpassword.getEditText().getText().toString();
                String Phone= txt_phone.getEditText().getText().toString();

                UserHelperClass helperClass= new UserHelperClass(First_name,Last_name,Email,Pwd,confPwd,Phone);

                reference.setValue(helperClass);

                Register();  //Registering Email for Authentication


            }
        });
    }

    private void Register(){
        String Email= txt_email.getEditText().getText().toString();
        String Password= txt_password.getEditText().getText().toString();
        String ConfPassword= txt_confpassword.getEditText().getText().toString();

        if(TextUtils.isEmpty(Email)){
            txt_email.setError("Enter your Email");
            return;
        }
        else if(TextUtils.isEmpty(Password)){
            txt_email.setError("Enter your password");
            return;
        }
        else if(TextUtils.isEmpty(ConfPassword)){
            txt_confpassword.setError("Confirm your password");
            return;
        }
        else if(!Password.equals(ConfPassword)){
            txt_confpassword.setError("Please match your password");
            return;
        }
        else if(Password.length() < 6){
            txt_password.setError("Password should be > 6");
            return;
        }
        else if(!isValidEmail(Email)){
            txt_email.setError("Invalid Email");
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Chef_Registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(Chef_Registration.this, Chef_Home.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Chef_Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}