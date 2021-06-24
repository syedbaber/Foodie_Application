package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class User_Registration extends AppCompatActivity {

    private TextInputLayout txt_fname, txt_lname, txt_email, txt_password, txt_confpassword, txt_phone;
   private Button btn_register;
   private FirebaseDatabase rootNode;
   private DatabaseReference reference;
   private FirebaseAuth firebaseAuth;
   private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);

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


                 rootNode= FirebaseDatabase.getInstance();

                Register();  //Registering Email for Authentication


            }
        });
    }

    private void Register(){
        String Fname= txt_fname.getEditText().getText().toString();
        String Lname= txt_lname.getEditText().getText().toString();
        String Email= txt_email.getEditText().getText().toString();
        String Password= txt_password.getEditText().getText().toString();
        String ConfPassword= txt_confpassword.getEditText().getText().toString();
        String Phn= txt_phone.getEditText().getText().toString();

        if(TextUtils.isEmpty(Fname)){
            txt_fname.setError("Enter First Name");
            txt_fname.setFocusableInTouchMode(true);
            txt_fname.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(Lname)){
            txt_lname.setError("Enter Last Name");
            return;
        }

        else if(TextUtils.isEmpty(Email)){
            txt_email.setError("Enter your Email");
            return;
        }
        else if(TextUtils.isEmpty(Password)){
            txt_password.setError("Enter your password");
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
        else if(TextUtils.isEmpty(Phn) || Phn.length() <10){
            txt_phone.setError("Enter valid phone number");
            return;
        }

        else {

            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            if (netInfo == null) {
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }

            else {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        //--------------- Adding Registered Userdata to firebase
                        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        reference = FirebaseDatabase.getInstance().getReference("User").child(useridd);

                        //Get all the values
                        String First_name = txt_fname.getEditText().getText().toString();
                        String Last_name = txt_lname.getEditText().getText().toString();
                        String email = txt_email.getEditText().getText().toString();
                        String Pwd = txt_password.getEditText().getText().toString();
                        String confPwd = txt_confpassword.getEditText().getText().toString();
                        String Phone = txt_phone.getEditText().getText().toString();

                        UserHelperClass helperClass = new UserHelperClass(First_name, Last_name, email, Pwd, confPwd, Phone);

                        reference.setValue(helperClass);

                        //------------------

                        Toast.makeText(User_Registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(User_Registration.this, User_Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(User_Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
            }
        }


    }

    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}