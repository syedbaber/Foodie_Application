package com.example.foodapp.AdminFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.R;

public class Dishes_Corner_Fragment extends Fragment {

    Button postDish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_admin_profile, null);
        getActivity().setTitle("Post Dish");

        postDish=(Button)v.findViewById(R.id.btn_postDish);

        postDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), Admin_Post_Dish.class);
                startActivity(intent);
            }
        });


        return v;
    }

}
