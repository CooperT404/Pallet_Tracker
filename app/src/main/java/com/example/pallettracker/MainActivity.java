package com.example.pallettracker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private LinearLayout Pallet_Info_ButtonsList;
    private DataBaseHandler dataHandler;
    private int total_Pallets = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Nav Buttons too different pages
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pallet_Info_ButtonsList = findViewById(R.id.Pallet_Info_ButtonsList);

        dataHandler = new DataBaseHandler(this);
        dataHandler.open();

        Pallet_Info_ButtonsList = findViewById(R.id.Pallet_Info_ButtonsList);

        Button calculator = (Button) findViewById(R.id.b_To_Calc);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_calc = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent_calc);
            }
        });
        Button notes = (Button) findViewById(R.id.b_To_Notes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Prompt_Pallet_Info.class);
//                startActivity(intent);
            }
        });
        Button archive = (Button) findViewById(R.id.b_To_Archive);
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Prompt_Pallet_Info.class);
//                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------------


        Button AddPallet = findViewById(R.id.New_Pallet);
        AddPallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                total_Pallets += 1;


                addButton(view);


                showAddButtonDialog();
            }
        });



    }
    private void showAddButtonDialog(){
        View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pallet Information");


        builder.setView(customLayout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                int units_int;
                int price_int;
                EditText Company = customLayout.findViewById(R.id.Company);
                EditText Supplier = customLayout.findViewById(R.id.Supplier);
                EditText Units = customLayout.findViewById(R.id.TotalNumber_Units);
                EditText Price = customLayout.findViewById(R.id.TotalNumber_Price);

                String Company_Input = Company.getText().toString();
                String Supplier_Input = Supplier.getText().toString();
                String Units_Input = Units.getText().toString();
                String Price_Input = Price.getText().toString();

                if(Units_Input != null) {
                    units_int = Integer.parseInt(Units_Input);
                }
                else {
                    units_int = 0;
                }
                if(Price_Input != null){
                    price_int = Integer.parseInt(Price_Input);
                }
                else {
                    price_int = 0;
                }

                long result = dataHandler.insertData(total_Pallets, Company_Input, Supplier_Input, units_int, price_int);


                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
    private void addButton(View view){
        Button newButton = new Button(this);
        newButton.setText("Pallet: " + total_Pallets);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataFromDatabase();
            }
        });

        Pallet_Info_ButtonsList.addView(newButton);

    }
    private void loadDataFromDatabase(){
        dataHandler.open();

        Cursor cursor = dataHandler.getAllData(1);

        if (cursor != null && cursor.moveToFirst()){
            @SuppressLint("Range") int palletNum = cursor.getInt(cursor.getColumnIndex("Pallet_Num"));
            @SuppressLint("Range") String company = cursor.getString(cursor.getColumnIndex("Company"));
            @SuppressLint("Range") String supplier = cursor.getString(cursor.getColumnIndex("Supplier"));
            @SuppressLint("Range") int units = cursor.getInt(cursor.getColumnIndex("Total_Units"));
            @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("Price"));

            EditText Comp = findViewById(R.id.Company);
            EditText Supp = findViewById(R.id.Supplier);
            EditText Text_Units = findViewById(R.id.TotalNumber_Units);
            EditText Text_Price = findViewById(R.id.TotalNumber_Price);

            Comp.setText(company);
            Supp.setText(supplier);
            Text_Units.setText(units);
            Text_Price.setText(price);

            cursor.close();
        }
        dataHandler.close();

    }

}

