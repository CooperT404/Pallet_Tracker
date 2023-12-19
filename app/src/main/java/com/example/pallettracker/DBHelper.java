package com.example.pallettracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Product_Info";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables for the needed parameters
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Product_Info (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Pallet_Num INTEGER," +
                "Company TEXT," +
                "Supplier TEXT," +
                "Total_Units INTEGER," +
                "Price INTEGER)";

        db.execSQL(createTableQuery);
    }

    // Upgrade database (if needed)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
    }
}