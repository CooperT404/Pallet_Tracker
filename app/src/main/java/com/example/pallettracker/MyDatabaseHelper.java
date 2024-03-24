package com.example.pallettracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;
import android.provider.BaseColumns;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Pallet_Tracker_Database";
    private static final int DATABASE_VERSION = 1;

    // Define your table and column names
    public static final String TABLE_NAME = "my_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_SUPPLIER = "supplier";
    public static final String COLUMN_UNITS = "units";
    public static final String COLUMN_COST = "cost";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        getWritableDatabase();
        Log.d("Database", "onCreate Called");
        // Create the table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COMPANY + " TEXT NOT NULL, " +
                COLUMN_SUPPLIER + " TEXT NOT NULL, " +
                COLUMN_UNITS + " INTEGER, " +
                COLUMN_COST + " REAL);";

        db.execSQL(createTableQuery);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
        // For simplicity, we won't cover this in detail here
    }

    // Insert data into the table
    public long insertData( String company, String supplier, int units, float cost) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID, ID);
        values.put(COLUMN_COMPANY, company);
        values.put(COLUMN_SUPPLIER, supplier);
        values.put(COLUMN_UNITS, units);
        values.put(COLUMN_COST, cost);
        return db.insert(TABLE_NAME, null, values);
    }
}
