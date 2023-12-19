package com.example.pallettracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private LinearLayout Pallet_Info_ButtonsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Nav Buttons too different pages
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                showAddButtonDialog();
            }
        });



    }
    private void showAddButtonDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pallet Information");

        View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                EditText Company = customLayout.findViewById(R.id.Company);
                EditText Supplier = customLayout.findViewById(R.id.Supplier);
                EditText Units = customLayout.findViewById(R.id.TotalNumber_Units);
                EditText Price = customLayout.findViewById(R.id.TotalNumber_Price);

                String Company_Input = Company.getText().toString();
                String Supplier_Input = Supplier.getText().toString();
                String Units_Input = Units.getText().toString();
                String Price_Input = Price.getText().toString();


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
    private void addButton(String label){
        Button newButton = new Button(this);
        newButton.setText(label);

    }
}

