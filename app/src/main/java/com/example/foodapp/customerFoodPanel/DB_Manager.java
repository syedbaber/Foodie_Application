package com.example.foodapp.customerFoodPanel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Manager extends SQLiteOpenHelper {

    private static final String dbName= "customer.db";

    public DB_Manager(Context context) {                //Constructor
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table tbl_cart (id integer primary key autoincrement, dishName text, price text, description text, imageURL text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query= "DROP TABLE IF EXISTS tbl_cart";
        db.execSQL(query);
        onCreate(db);
    }

    public String insertDbData(String dishName, String price, String description, String imageURL){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("dishName", dishName);
        values.put("price",price);
        values.put("description",description);
        values.put("imageURL", imageURL);

        long result = db.insert("tbl_cart",null,values); //Non-negative and negative values. If insertion Failed negative value will return and vice versa.
        if(result==-1){
            return "Failed to add Cart";
        }
        else {
            return "Dish Successfully Added to Cart.";
        }
    }

    public Cursor getDbData(){
        SQLiteDatabase db= this.getWritableDatabase();
        String query= "select * from tbl_cart order by id desc";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }
}
