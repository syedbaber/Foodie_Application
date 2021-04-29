//package com.example.foodapp.AdminFoodPanel.Service;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//import com.bumptech.glide.request.Request;
//import com.example.foodapp.AdminFoodPanel.Admin_Orders_Fragment;
//import com.example.foodapp.Model.Request_Order_Model;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class ListenOrder extends Service implements ChildEventListener {
//    FirebaseDatabase db;
//    DatabaseReference dbReference;
//    public ListenOrder() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return  null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        db= FirebaseDatabase.getInstance();
//        dbReference= db.getReference().child("Order_Requests");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        dbReference.addChildEventListener(this);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//    }
//
//    @Override
//    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//        //Trigger the notification
//      //  Request request= snapshot.getValue(Request.class);
//        Request_Order_Model request_order_model= snapshot.getValue(Request_Order_Model.class);
//        showNotification(snapshot.getKey(),request_order_model);
//    }
//
//    private void showNotification(String key, Request_Order_Model request_order_model) {
//        Intent intent= new Intent(this, Admin_Orders_Fragment.class);
//        intent.putExtra("UserPhone", request_order_model.getPhone());  // Change it as needed for Email
//        PendingIntent contentIntent= PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder= new NotificationCompat.Builder(getBaseContext());
//        builder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setTicker("Foodie")
//                .setContentInfo("Your order was updated")
//                .setContentText("Order# "+key+" was update status to "+convertCodeToStatus(request_order_model.getStatus()))
//                .setContentIntent(contentIntent)
//                .setContentInfo("Info");
//
//        NotificationManager notificationManager= (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1,builder.build());
//    }
//
//
//    @Override
//    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//    }
//
//    @Override
//    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//
//    private String convertCodeToStatus(String status) {
//        String txt_Status = null;
//        if(status.equals("0") )
//            txt_Status= "Order placed.";
//
//        else if(status.equals("1"))
//            txt_Status= "On my way.";
//
//        else if (status.equals("2"))
//            txt_Status= "Order delivered";
//
//        return txt_Status;
//    }
//}