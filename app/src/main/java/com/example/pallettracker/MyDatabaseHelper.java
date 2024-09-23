package com.example.pallettracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                + COLUMN_ID + " INTEGER ,"
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

    public List<String> getUniqueIDs() {
        List<String> uniqueIDs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_ID + " FROM " + TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (columnIndex != -1) {
                    uniqueIDs.add(cursor.getString(columnIndex));
                } else {
                    Log.e("Database", "Column not found: " + COLUMN_ID);
                }
            } while (cursor.moveToNext());
            cursor.close();

        }

        return uniqueIDs;
    }
    public List<String> getIDsByTag(String tag) {
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{tag});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (columnIndex != -1) {
                    ids.add(cursor.getString(columnIndex));
                } else {
                    Log.e("Database", "Column not found: " + COLUMN_ID);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return ids;
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
    public void updateData(int rowId, int rowIndex, String unitsName, String units, String supplier, String cost, String company) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_UNITS_NAME, unitsName);
        contentValues.put(COLUMN_UNITS, units);
        contentValues.put(COLUMN_SUPPLIER, supplier);
        contentValues.put(COLUMN_COST, cost);
        contentValues.put(COLUMN_COMPANY, company);

        Cursor cursor = null;

        try {
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = {String.valueOf(rowId)};
            Log.d("Database", "Executing query on table " + TABLE_NAME + " with selection " + selection + " and args " + Arrays.toString(selectionArgs));

            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToPosition(rowIndex)) {
                @SuppressLint("Range") int rowIdIndex = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String whereClause = COLUMN_ID + "=?";
                String[] whereArgs = {String.valueOf(rowIdIndex)};
                int rowsUpdated = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
                Log.d("Database", "Rows updated: " + rowsUpdated);
            } else {
                Log.e("Database", "Cursor is null or could not move to position " + rowIndex);
            }
        } catch (Exception e) {
            Log.e("Database", "Error while updating data in database: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }


    public String getStringFromDatabase(int rowId, String columnName, int rowIndex) {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = "";
        Cursor cursor = null;

        try {
            String selection = COLUMN_ID + "=?";
            String[] selectionArgs = {String.valueOf(rowId)};
            Log.d("Database", "Executing query on table " + TABLE_NAME + " with selection " + selection + " and args " + Arrays.toString(selectionArgs));

            cursor = db.query(TABLE_NAME, new String[]{columnName}, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToPosition(rowIndex)) {
                int columnIndex = cursor.getColumnIndex(columnName);
                if (columnIndex != -1) {
                    result = cursor.getString(columnIndex);
                    Log.d("Database", "Retrieved value: " + result);
                } else {
                    Log.e("Database", "Column not found: " + columnName);
                }
            } else {
                Log.e("Database", "Cursor is null or could not move to position " + rowIndex);
            }
        } catch (Exception e) {
            Log.e("Database", "Error while getting data from database: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        Log.d("DatabaseHelper", "Retrieved data for column " + columnName + ": " + result);
        return result;
    }







}
