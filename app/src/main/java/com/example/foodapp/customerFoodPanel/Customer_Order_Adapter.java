package com.example.foodapp.customerFoodPanel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.LatLng_Model;
import com.example.foodapp.Model.Request_Order_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Customer_Order_Adapter extends FirebaseRecyclerAdapter<Request_Order_Model, Customer_Order_Adapter.myViewHolder> {


    public Customer_Order_Adapter(@NonNull FirebaseRecyclerOptions<Request_Order_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position, @NonNull Request_Order_Model request_order_model) {
        myViewHolder.order_ID.setText("ID# " + getRef(position).getKey());
        myViewHolder.order_Status.setText(convertCodeToStatus(request_order_model.getStatus()));
        myViewHolder.order_Address.setText(request_order_model.getAddress());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Order_key = getRef(position).getKey();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Customer_Order_Details_Fragment(Order_key)).addToBackStack(null).commit();

                //----------Testing---------//

                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                LatLng_Model model= new LatLng_Model(
                        location.getLatitude(),
                        location.getLongitude()
                );

                FirebaseDatabase.getInstance().getReference("Order_Location").child(Order_key).setValue(model);

                //--------------------------//
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_order_status_single_row,parent,false);

        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView order_ID, order_Status, order_Address;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            order_ID= itemView.findViewById(R.id.order_ID);
            order_Status= itemView.findViewById(R.id.order_Status);
            order_Address= itemView.findViewById(R.id.order_Address);
            cardView= itemView.findViewById(R.id.customer_order_cardView);
        }
    }

    private String convertCodeToStatus(String status) {
        String txt_Status = null;
        if(status.equals("0") )
            txt_Status= "Order placed.";
        
        else if(status.equals("1"))
            txt_Status= "On my way.";
        
        else if (status.equals("2"))
            txt_Status= "Order delivered";
        
        return txt_Status;
    }
}
