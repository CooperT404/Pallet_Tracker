package com.example.pallettracker;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class DataBaseHandler extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DataBaseHandler(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertData(String company, String supplier, int units, double cost) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY, company);
        values.put(DBHelper.COLUMN_SUPPLIER, supplier);
        values.put(DBHelper.COLUMN_UNITS, units);
        values.put(DBHelper.COLUMN_COST, cost);

        return database.insert(DBHelper.TABLE_NAME, null, values);
    }

    public void updateData(int id, String company, String supplier, int units, double cost) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY, company);
        values.put(DBHelper.COLUMN_SUPPLIER, supplier);
        values.put(DBHelper.COLUMN_UNITS, units);
        values.put(DBHelper.COLUMN_COST, cost);

        String whereClause = DBHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};

        database.update(DBHelper.TABLE_NAME, values, whereClause, whereArgs);
    }

    public Cursor getAllData() {
        return database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
    }
}