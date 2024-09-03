package com.example.pallettracker;

import android.content.Context;
import android.database.Cursor;
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
    public static final String COLUMN_UNITS_NAME = "unitsName";
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
        String CREATE_MY_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_UNITS_NAME + " TEXT,"
                + COLUMN_UNITS + " TEXT,"
                + COLUMN_SUPPLIER + " TEXT,"
                + COLUMN_COST + " REAL,"
                + COLUMN_COMPANY + " TEXT" + ")";
        db.execSQL(CREATE_MY_TABLE);
    }

    public boolean doesUserIDExist(int userIDToCheck) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_ID}; // Only need the ID column for existence check
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userIDToCheck)};
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean userExists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return userExists;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
        // For simplicity, we won't cover this in detail here
    }
    //Deletes the contents of the database, WARNING
    public void onDelete(SQLiteDatabase db){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    // Insert data into the table
    public long insertData( int ID, String company, String supplier,String unitsName, int units, float cost) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, ID);
        values.put(COLUMN_COMPANY, company);
        values.put(COLUMN_SUPPLIER, supplier);
        values.put(COLUMN_UNITS_NAME, unitsName);
        values.put(COLUMN_UNITS, units);
        values.put(COLUMN_COST, cost);
        return db.insert(TABLE_NAME, null, values);

    }
    public int updateData(int idToUpdate, String newUnitsName, int newUnits, float newCost) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UNITS_NAME, newUnitsName);
        values.put(COLUMN_UNITS, newUnits);
        values.put(COLUMN_COST, newCost);

        // Specify the WHERE clause to update the row with the given ID
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(idToUpdate)};

        // Perform the update
        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
