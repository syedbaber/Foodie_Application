//package com.example.foodapp.Database;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteQueryBuilder;
//
//import com.example.foodapp.Model.Order;
//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Database extends SQLiteAssetHelper {
//    private static final String DB_NAME= "FoodAppDB.db";
//    private static final int DB_Version= 1;
//
//
//    public Database(Context context) {
//        super(context, DB_NAME, null, DB_Version);
//    }
//
//    public List<Order> getcarts(){
//        SQLiteDatabase db= getReadableDatabase();
//        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
//
//        String[] query = {"ID, DishName, Quantity, Price, ImageURL"};
//        String sqlTable= "OrderDetail";
//        qb.setTables(sqlTable);
//
//        Cursor c= qb.query(db,query,null,null,null,null,null);
//
//        final List<Order> result= new ArrayList<>();
//
//        if(c.moveToFirst()){
//            do{
//                result.add(new Order(c.getString(c.getColumnIndex("ID")),
//                        c.getString(c.getColumnIndex("DishName")),
//                        c.getString(c.getColumnIndex("Quantity")),
//                        c.getString(c.getColumnIndex("Price")),
//                        c.getString(c.getColumnIndex("ImageURL"))
//                ));
//            }while (c.moveToNext());
//        }
//
//        return result;
//
//    }
//
//    public void addToCart(Order order){
//        SQLiteDatabase db= getWritableDatabase();
//        String query= String.format("INSERT INTO OrderDetail (DishName, Quantity, Price, ImageURL) VALUES ('%s', '%s', '%s', '%s');");
//    }
//}
