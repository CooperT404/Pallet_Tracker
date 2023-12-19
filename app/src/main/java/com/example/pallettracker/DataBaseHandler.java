package com.example.pallettracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler {
    private SQLiteDatabase database;
    private DBHelper dbHelper;


    public DataBaseHandler(Context context){
        DBHelper dbHelper = new DBHelper(context);
    }
    // open database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    // close
    public void close(){
        dbHelper.close();
    }

    // Insert data

    public long insertData(int columnValues, String column1Value, String column2Value , int column3Value, int column4Values){
        ContentValues values = new ContentValues();
        values.put("Pallet Number" , columnValues);
        values.put("Company", column1Value);
        values.put("Supplier", column2Value);
        values.put("Units", column3Value);
        values.put("Price", column4Values);

        return database.insert("Product_Info", null, values);
    }
    // retrieve data
    public Cursor getAllData(){
        return database.query("Product_Info", null, null, null, null, null, null);
    }

}
