package com.example.foodapp.AdminFoodPanel;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Model.Rider_Registration_Model;
import com.example.foodapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Riders_List_Adapter extends FirebaseRecyclerAdapter<Rider_Registration_Model, Riders_List_Adapter.myViewHolder> {

    public Riders_List_Adapter(@NonNull FirebaseRecyclerOptions<Rider_Registration_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull Rider_Registration_Model rider_registration_model) {
        myViewHolder.Rider_name.setText(rider_registration_model.getFname()+" "+ rider_registration_model.getLname());
        myViewHolder.Rider_status.setText(convertCodeToStatus(rider_registration_model.getStatus()));

        if(myViewHolder.Rider_status.getText() == "Working"){
            myViewHolder.Rider_status.setTextColor(Color.parseColor("#ff002b"));
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.Rider_status.getText() == "Idle"){
                    myViewHolder.btn_DeliverToRider.setVisibility(View.VISIBLE);
                }
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
