package com.example.foodapp.AdminFoodPanel;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.Deliver_to_Rider_Model;
import com.example.foodapp.Model.Rider_Registration_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Riders_List_Adapter extends FirebaseRecyclerAdapter<Rider_Registration_Model, Riders_List_Adapter.myViewHolder> {
    String Order_id, customer_Name;

    public Riders_List_Adapter(@NonNull FirebaseRecyclerOptions<Rider_Registration_Model> options, String order_id, String Customer_Name) {
        super(options);
        Order_id = order_id;
        customer_Name=Customer_Name;
    }

    public Riders_List_Adapter(@NonNull FirebaseRecyclerOptions<Rider_Registration_Model> options, Riders_List riders_list) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull Rider_Registration_Model rider_registration_model) {
        myViewHolder.Rider_name.setText(rider_registration_model.getFname()+" "+ rider_registration_model.getLname());
        myViewHolder.Rider_status.setText(convertCodeToStatus(rider_registration_model.getStatus()));


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myViewHolder.btn_DeliverToRider.setVisibility(View.VISIBLE);
            }
        });

        myViewHolder.btn_DeliverToRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Rider_ID= getRef(i).getKey();

                //Adding values to store in firebase
                Deliver_to_Rider_Model deliver_to_rider_model= new Deliver_to_Rider_Model(Order_id,Rider_ID, customer_Name);

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("order_Delivery");
                databaseReference.child(Order_id).setValue(deliver_to_rider_model);

                //Changing status of Rider from 'Idle' to 'Working'.
                DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Riders");
                databaseReference1.child(Rider_ID).child("status").setValue("1");

                //Changing Order status from 'Order Placed' to 'On My Way'.
                DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("Order_Requests");
                databaseReference2.child(Order_id).child("status").setValue("1");

               //Display Message
                Snackbar.make(v, "Order Handover to Rider Successfully.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //Go back to orders fragments
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Admin_Orders_Fragment()).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.riders_list_single_row,parent,false);

        return new Riders_List_Adapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView Rider_name, Rider_status;
        CardView cardView;
        Button btn_DeliverToRider;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Rider_name= itemView.findViewById(R.id.Rider_name);
            Rider_status= itemView.findViewById(R.id.Rider_Status);
            cardView= itemView.findViewById(R.id.riderList_Cardview);
            btn_DeliverToRider= itemView.findViewById(R.id.btn_deliverToRider);

        }
    }

    private String convertCodeToStatus(String status) {
        String txt_Status = null;
        if(status.equals("0") )
            txt_Status= "Idle";

        else if(status.equals("1"))
            txt_Status= "Working";

        return txt_Status;
    }
}
