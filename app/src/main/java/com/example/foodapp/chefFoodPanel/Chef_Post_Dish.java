package com.example.foodapp.chefFoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.foodapp.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Chef_Post_Dish extends AppCompatActivity {

    ImageButton imageButton;
    Button post_dish;
    TextInputLayout dishN, desc,qty,pri;
    String dishName,descrption,quantity,price;
    Uri imageuri;
    private Uri mcropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dataa;
    FirebaseAuth Fauth;
    StorageReference ref;
    String ChefId , RandomUID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.foodapp.R.layout.activity_chef__post__dish);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dishN=findViewById(R.id.dishName);
        desc = findViewById(R.id.description);
        qty = findViewById(R.id.Quantity);
        pri = findViewById(R.id.price);
        post_dish = findViewById(R.id.btn_postDish);
        Fauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = FirebaseDatabase.getInstance().getReference("Chef").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    imageButton = findViewById(R.id.image_upload);

                    imageButton.setOnClickListener(v -> onSelectImageclick(v));

                    post_dish.setOnClickListener(v -> {
                        dishName = dishN.getEditText().getText().toString().trim();
                        descrption = desc.getEditText().getText().toString().trim();
                        quantity = qty.getEditText().getText().toString().trim();
                        price = pri.getEditText().getText().toString().trim();

                        if(isValid()){
                            uploadImage();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }

    }

    private void uploadImage() {

        if(imageuri != null){
            final ProgressDialog progressDialog = new ProgressDialog(Chef_Post_Dish.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageuri).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {

                FoodDetails info = new FoodDetails(dishName,descrption,quantity,price,String.valueOf(uri),RandomUID,ChefId);
                FirebaseDatabase.getInstance().getReference("FoodDetails").child(RandomUID)
                        .setValue(info).addOnCompleteListener(task -> {

                            progressDialog.dismiss();
                            Toast.makeText(Chef_Post_Dish.this,"Dish Posted Successfully!",Toast.LENGTH_SHORT).show();
                            imageButton.setBackgroundResource(R.drawable.ic_camera_24);
                            dishN.getEditText().getText().clear();
                            desc.getEditText().getText().clear();
                            qty.getEditText().getText().clear();
                            pri.getEditText().getText().clear();
                        });

            })).addOnFailureListener(e -> {
                progressDialog.dismiss();
                Toast.makeText(Chef_Post_Dish.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }).addOnProgressListener(taskSnapshot -> {
                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded "+(int) progress+"%");
                progressDialog.setCanceledOnTouchOutside(false);
            });
        }

    }

    private boolean isValid() {

        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");

        boolean isValidDescription = false,isValidPrice=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(dishName)){
            dishN.setErrorEnabled(true);
            desc.setError("Dish Name is Required");
        }
        if(TextUtils.isEmpty(descrption)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        }else{
            desc.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter number of Plates or Items");
        }else{
            isValidQuantity=true;
        }
        if(TextUtils.isEmpty(price)){
            pri.setErrorEnabled(true);
            pri.setError("Please Mention Price");
        }else{
            isValidPrice=true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidPrice)?true:false;
        return isValid;
    }
    private void startCropImageActivity(Uri imageuri){
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private void onSelectImageclick(View v){
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mcropimageuri !=null && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            startCropImageActivity(mcropimageuri);
        }else{
            Toast.makeText(this,"Cancelling! Permission Not Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imageuri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
                mcropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imageuri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                ((ImageButton) findViewById(R.id.image_upload)).setImageURI(result.getUri());
                Toast.makeText(this,"Cropped Successfully!",Toast.LENGTH_SHORT).show();
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this,"Failed To Crop"+result.getError(),Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}