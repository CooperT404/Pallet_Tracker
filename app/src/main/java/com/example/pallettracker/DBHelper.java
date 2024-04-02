package com.example.pallettracker;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "YourDatabaseName";
    private static final int DATABASE_VERSION = 1;

    // Table and columns
    public static final String TABLE_NAME = "YourTableName";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_COMPANY = "Company";
    public static final String COLUMN_SUPPLIER = "Supplier";
    public static final String COLUMN_UNITS = "Units";
    public static final String COLUMN_COST = "Cost";

    // Create table query
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_COMPANY + " TEXT," +
            COLUMN_SUPPLIER + " TEXT," +
            COLUMN_UNITS + " INTEGER," +
            COLUMN_COST + " REAL" +
            ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement upgrade logic here if needed
    }



    @SuppressLint("Range")
    public List<DataModel> getAllData(int e){

        List<DataModel> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + e ,null);
        if(cursor.moveToFirst()){
            do {
                DataModel data = new DataModel();
                data.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                data.setCompany(cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY)));
                data.setSupplier(cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER)));
                data.setUnits(cursor.getInt(cursor.getColumnIndex(COLUMN_UNITS)));
                data.setCost(cursor.getDouble(cursor.getColumnIndex(COLUMN_COST)));


            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dataList;
    }
}