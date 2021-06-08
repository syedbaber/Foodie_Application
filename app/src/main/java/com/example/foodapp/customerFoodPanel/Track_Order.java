package com.example.foodapp.customerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.foodapp.Model.LatLng_Model;
import com.example.foodapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Track_Order extends AppCompatActivity implements GoogleMap.OnMyLocationChangeListener {
    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    
    private int REQUEST_CODE = 111;

    private String Order_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track__order);

        Order_ID= getIntent().getExtras().getString("Order_key", "defaultKey");
        Toast.makeText(this, Order_ID, Toast.LENGTH_SHORT).show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.trackOrderMap);

        client = LocationServices.getFusedLocationProviderClient(Track_Order.this);

        if (ActivityCompat.checkSelfPermission(Track_Order.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        }
        
        else {
            ActivityCompat.requestPermissions(Track_Order.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());

                            MarkerOptions markerOptions= new MarkerOptions().position(latLng).title("You are here!");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
                            googleMap.addMarker(markerOptions).showInfoWindow();


                            LatLng_Model model= new LatLng_Model(
                                    location.getLatitude(),
                                    location.getLongitude()
                            );

                            FirebaseDatabase.getInstance().getReference("Order_Location").child(Order_ID).setValue(model);
                        }
                    });
                }
            }
        });   
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
            else {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        LatLng_Model model= new LatLng_Model(
                location.getLatitude(),
                location.getLongitude()
        );

        FirebaseDatabase.getInstance().getReference("Order_Location").child(Order_ID).setValue(model);

    }

}