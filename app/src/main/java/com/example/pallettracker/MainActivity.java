package com.example.pallettracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LinearLayout Pallet_Info_ButtonsList;

    private DataBaseHandler dataHandler;
    private DBHelper dbHelper = new DBHelper(this);
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
                showAddButtonDialog(view);
            }
        });




    }

    private void showAddButtonDialog(View view){
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

               int inUnits = Integer.parseInt(Units_Input);
               int inPrice = Integer.parseInt(Price_Input);

                dataHandler.insertData(Company_Input, Supplier_Input, inUnits, inPrice);

                addButton(view);

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
        newButton.setText("Pallets: " + total_Pallets);

        int buttonId = View.generateViewId();
        newButton.setId(buttonId);

        newButton.setTag(total_Pallets);

        View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);

        AlertDialog.Builder Editbuilder = new AlertDialog.Builder(this);
        Editbuilder.setTitle("Edit Pallet");
        Editbuilder.setView(customLayout);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer palletIndex = (Integer) newButton.getTag();

                if (palletIndex != null){
                    fetchData(palletIndex);
                    onPause();
                }
                else;

            }
        });

        Pallet_Info_ButtonsList.addView(newButton);


    }
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        Button button = findViewById(R.id.New_Pallet);
        boolean isButtonEnabled = button.isEnabled();
        editor.putBoolean("button_state", isButtonEnabled);

        editor.apply();
    }
    private void fetchData(int e){
        View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        List<DataModel> dataList = dbHelper.getAllData(e);

        if(!dataList.isEmpty() && e < dataList.size()){
            DataModel data = dataList.get(0);

            EditText companyEditText = customLayout.findViewById(R.id.Company);
            EditText supplierEditText = customLayout.findViewById(R.id.Supplier);
            EditText unitsEditText = customLayout.findViewById(R.id.TotalNumber_Units);
            EditText priceEditText = customLayout.findViewById(R.id.TotalNumber_Price);

            companyEditText.setText(data.getCompany());
            supplierEditText.setText(data.getSupplier());
            unitsEditText.setText(String.valueOf(data.getUnits()));
            priceEditText.setText(String.valueOf(data.getCost()));
        }
    }

}

