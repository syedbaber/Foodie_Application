package com.example.foodapp.customerFoodPanel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Customer_Cart_Fragment extends Fragment {
    RecyclerView recyclerView;
    TextView total_Price;
    Button deleteTable, btn_placeOrder;
    ArrayList<SQL_DB_Model> dataHolder;
    int total=0;
    String userName, userPhone, UID;

    FirebaseDatabase database;
    DatabaseReference request;
    FirebaseAuth firebaseAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer__cart_, container, false);

        //Set Title
        getActivity().setTitle("Cart");

        recyclerView=view.findViewById(R.id.cart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deleteTable= view.findViewById(R.id.deleteTable);
        btn_placeOrder= view.findViewById(R.id.btn_placeOrder);
        total_Price= view.findViewById(R.id.total_Price);

        //Firebase
        database= FirebaseDatabase.getInstance();
        request= database.getReference("Order_Requests");
        UID =  FirebaseAuth.getInstance().getCurrentUser().getUid();



        Cursor cursor= new DB_Manager(getContext()).getDbData();
        dataHolder=new ArrayList<>();

        while (cursor.moveToNext()){
            SQL_DB_Model obj= new SQL_DB_Model(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            dataHolder.add(obj);

            //Calculating Total Price
            total+=  (Integer.parseInt(obj.getPrice())) * (Integer.parseInt(obj.getQuantity()));
        }

        Customer_Cart_Adapter adapter= new Customer_Cart_Adapter(dataHolder);
        recyclerView.setAdapter(adapter);

        //Assigning Total value to TextView
       total_Price.setText(String.valueOf(total));

       //Calling User Info function
        userInfo();


       //Place Order Button
        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get address through Alter Dialog
                showAlertDialog();
            }



        });


        //Delete table
        deleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result= new DB_Manager(getContext()).DeleteTable();
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();


                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Customer_Home_Fragment()).commit();
            }
        });







        return view;
    }

    public void userInfo(){

        //Firebase
        String UID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference uidReference= FirebaseDatabase.getInstance().getReference().child("User").child(UID);


        uidReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fname= snapshot.child("fname").getValue(String.class);
                String lname= snapshot.child("lname").getValue(String.class);
                String phone= snapshot.child("phone").getValue(String.class);

                //Assigning
                userName= fname+ " " +lname;
                userPhone=phone;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("Enter your delivery address: ");

        final EditText edtAdress = new EditText(getContext());
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
           LinearLayout.LayoutParams.MATCH_PARENT
            );

        edtAdress.setLayoutParams(lp);
        alertDialog.setView(edtAdress);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request_Order_Model request_order_model= new Request_Order_Model(userName,
                        userPhone,
                        UID,
                        total_Price.getText().toString(),
                        edtAdress.getText().toString(),
                        dataHolder);

                if(total == 0){
                    Toast.makeText(getContext(), "Please Add Some Food to Cart", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Submit to Firebase
                    request.child(String.valueOf(System.currentTimeMillis())).setValue(request_order_model);

                    Toast.makeText(getContext(), "Order Placed Successfully...", Toast.LENGTH_SHORT).show();

                    //Clear Cart
                    new DB_Manager(getContext()).DeleteTable();
                }
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

}