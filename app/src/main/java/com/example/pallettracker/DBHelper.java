package com.example.pallettracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public static final class YourContract{
        private YourContract() {}

        public static final class YourTable implements BaseColumns {
            public static final String TABLE_NAME = "Pallets";
            public static final String COLUMN_NAME_DATA = "Pallet_Data";
        }


    }

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
    // add method to retrieve data from the database
    @SuppressLint("Range")
    public String getDataById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {YourContract.YourTable.COLUMN_NAME_DATA};
        String selection = YourContract.YourTable._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(
                YourContract.YourTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String data = null;

        if (cursor != null && cursor.moveToFirst()){
            data = cursor.getString(cursor.getColumnIndex(YourContract.YourTable.COLUMN_NAME_DATA));
            cursor.close();
        }
        return data;
    }

    // Upgrade database (if needed)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
    }
}