package com.example.foodapp.AdminFoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodapp.Common.Common;
import com.example.foodapp.R;
import com.example.foodapp.Remote.iGeoCoordinates;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Admin_Order_Tracking extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
        {

    private GoogleMap mMap;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST=1000;
    private static final int LOCATION_PERMISSION_REQUEST=1001;

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static int UPDATE_INTERVAL=1000;
    private static int FASTEST_INTERVAL=5000;
    private static int DISPLACEMENT= 10;

    private iGeoCoordinates mServices;

    private static String clientAddress;

    private String TAG = "so47492459";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__order__tracking);

        mServices = Common.getGeoCodeService();

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestRunTimePermission();
        }
        else {
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
            }
        }

        displayLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

            private void displayLocation() {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestRunTimePermission();
                }
                else {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    if(mLastLocation != null){
                        double latitude = mLastLocation.getLatitude();
                        double longitude = mLastLocation.getLongitude();

                        //Adding marker on our location and moving camera.
                        LatLng yourLocation = new LatLng(latitude,longitude);
                        mMap.addMarker(new MarkerOptions().position(yourLocation).title("Your Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(9.0f));

                        //-------------Getting order address from Order Adapter----------//
                        Intent intent= getIntent();
                        clientAddress=intent.getStringExtra("clientAddress");


                        //Point marker on client address
                      getLocationFromAddress(getApplicationContext(), clientAddress);  //Calling fun for pointer as well as get client LatLng.


                        //--------------------------//


                    }
                    else {
                        Toast.makeText(this, "Couldn't get your location.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

//            private void drawRoute(LatLng yourLocation, String clientAddress) {
//                mServices.getGeoCode(clientAddress).enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        try {
//                            JSONObject jsonObject= new JSONObject(response.body());
//
//                            String lat= ((JSONArray)jsonObject.get("results"))
//                                    .getJSONObject(0)
//                                    .getJSONObject("geometry")
//                                    .getJSONObject("location")
//                                    .get("lat").toString();
//
//                            String lng= ((JSONArray)jsonObject.get("results"))
//                                    .getJSONObject(0)
//                                    .getJSONObject("geometry")
//                                    .getJSONObject("location")
//                                    .get("lng").toString();
//
////                            LatLng orderLocation= new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
//                            LatLng orderLocation= new LatLng(34.149502, 73.199501);
//
//                            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_home_pink);
//                            bitmap= Common.scaleBitmap(bitmap,70,70);
//
//                            MarkerOptions marker= new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//                                    .title("clientAddress")   //You can title whatever you want
//                                    .position(orderLocation);
//                            mMap.addMarker(marker);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });
//            }

            private void createLocationRequest() {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(UPDATE_INTERVAL);
                mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
                mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
            }

            protected synchronized void buildGoogleApiClient() {
                mGoogleApiClient= new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API).build();
                mGoogleApiClient.connect();
            }

            private boolean checkPlayServices() {
                int resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                if(resultCode != ConnectionResult.SUCCESS){
                    if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                        GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                    }
                    else {
                        Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return false;
                }
                return true;
            }


            private void requestRunTimePermission() {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        }, LOCATION_PERMISSION_REQUEST);
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                switch (requestCode) {
                    case LOCATION_PERMISSION_REQUEST:
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if(checkPlayServices()){
                                buildGoogleApiClient();
                                createLocationRequest();

                                displayLocation();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

            @Override
            public void onConnected(@Nullable Bundle bundle) {
                displayLocation();
                startLocationUpdates();
            }

            private void startLocationUpdates() {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
            }

            @Override
            public void onConnectionSuspended(int i) {
                mGoogleApiClient.connect();
            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }

            @Override
            public void onLocationChanged(Location location) {
                mLastLocation= location;
                displayLocation();
            }

            @Override
            protected void onResume() {
                super.onResume();
                checkPlayServices();
            }

            @Override
            protected void onStart() {
                super.onStart();

                if(mGoogleApiClient != null){
                    mGoogleApiClient.connect();
                }
            }


            public LatLng getLocationFromAddress(Context context, String strAddress) {

                Geocoder coder = new Geocoder(context);
                List<Address> address;
                LatLng p1 = null;

                try {
                    address = coder.getFromLocationName(strAddress, 5);
                    if (address == null) {
                        return null;
                    }

                    Address location = address.get(0);
                    p1 = new LatLng(location.getLatitude(), location.getLongitude() );

                    //-------Testing Bitmap---------//
//                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_home_pink);
//                    bitmap= Common.scaleBitmap(bitmap,70,70);

                    //----------------------------//

                    mMap.addMarker(new MarkerOptions().position(p1).title("order Location"));

                } catch (IOException ex) {

                    ex.printStackTrace();
                }

                return p1;
            }

//            private void drawRoute(LatLng yourLocation, LatLng clientLatLng) {
//                //Define list to get all latlng for the route
//                List<LatLng> path = new ArrayList();
//
//
//                //Execute Directions API request
//
//                GeoApiContext context = new GeoApiContext.Builder()
//                        .apiKey("AIzaSyD9tfhEcy8V6--gpSQ-JQyj0EWKIZyZAuw")
//                        .build();
//
//                DirectionsApiRequest req = DirectionsApi.getDirections(context, "33.994648,72.910622", "34.149502,73.199501");
//                try {
//                    DirectionsResult res = req.await();
//
//                    //Loop through legs and steps to get encoded polylines of each step
//                    if (res.routes != null && res.routes.length > 0) {
//                        DirectionsRoute route = res.routes[0];
//
//                        if (route.legs !=null) {
//                            for(int i=0; i<route.legs.length; i++) {
//                                DirectionsLeg leg = route.legs[i];
//                                if (leg.steps != null) {
//                                    for (int j=0; j<leg.steps.length;j++){
//                                        DirectionsStep step = leg.steps[j];
//                                        if (step.steps != null && step.steps.length >0) {
//                                            for (int k=0; k<step.steps.length;k++){
//                                                DirectionsStep step1 = step.steps[k];
//                                                EncodedPolyline points1 = step1.polyline;
//                                                if (points1 != null) {
//                                                    //Decode polyline and add points to list of route coordinates
//                                                    List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
//                                                    for (com.google.maps.model.LatLng coord1 : coords1) {
//                                                        path.add(new LatLng(coord1.lat, coord1.lng));
//                                                    }
//                                                }
//                                            }
//                                        } else {
//                                            EncodedPolyline points = step.polyline;
//                                            if (points != null) {
//                                                //Decode polyline and add points to list of route coordinates
//                                                List<com.google.maps.model.LatLng> coords = points.decodePath();
//                                                for (com.google.maps.model.LatLng coord : coords) {
//                                                    path.add(new LatLng(coord.lat, coord.lng));
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } catch(Exception ex) {
//                    Log.e(TAG, ex.getLocalizedMessage());
//                }
//
//                //Draw the polyline
//                if (path.size() > 0) {
//                    PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
//                    mMap.addPolyline(opts);
//
//                    mMap.getUiSettings().setZoomControlsEnabled(true);
//
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( 6));
//                }
//            }
        }

