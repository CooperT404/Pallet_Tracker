package com.example.pallettracker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.database.Cursor;
import android.widget.ScrollView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private LinearLayout verticalLayout;
    private layoutButtons LB;





    private int total_Pallets = 1;
    private int unique_Units = 0;
    private int RandNum = 0;
    private int Max = 99999;
    private int Min = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Nav Buttons too different pages
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);
        verticalLayout = findViewById(R.id.Pallet_Info_ButtonsList);

        //new random instance
        Random random = new Random();

        Button calculator = (Button) findViewById(R.id.b_To_Calc);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_calc = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent_calc);
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
//this is a function to delete the table using the DELETE button in the main activity --DO NOT USE IF YOU DON'T WANT TO WIPE THE DATABASE--

        Button Delete = (Button) findViewById(R.id.Delete_Pallet);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                dbHelper.onDelete(db);

            }
        });
        //------------------------------------------------------------------------------------


        Button AddPallet = findViewById(R.id.New_Pallet);
        AddPallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                total_Pallets += 1;
                do {
                    Log.d("MyApp", "in random loop");
                    RandNum = random.nextInt(Max - Min + 1) + Min;
                }while(dbHelper.doesUserIDExist(RandNum) == true);
                createNumberedButtons(total_Pallets);
                showInputDialog();
            }
        });

    }



    protected void showInputDialog() {
        LB = new layoutButtons();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.custom_layout, null);
        View Items = layoutInflater.inflate(R.layout.new_pallet_unique_units, null);

        AlertDialog.Builder getNum = new AlertDialog.Builder(this);
        getNum.setView(Items);
        getNum.setTitle("Unique Pallets");

        final EditText companyEditText = promptView.findViewById(R.id.Company);
        final EditText supplierEditText = promptView.findViewById(R.id.Supplier);
        final EditText costEditText = promptView.findViewById(R.id.TotalNumber_Price);
        final EditText totalEditText = promptView.findViewById(R.id.Total_Units);
        final EditText Names = promptView.findViewById(R.id.Unit_Names);

        getNum.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText foundNum = Items.findViewById(R.id.UniqueUnits);
                        String Uu = foundNum.getText().toString();

                        try {
                            int UU = Integer.parseInt(Uu);
                            unique_Units = UU;
                            Log.d("MyApp", "Number: " + UU);
                        } catch (NumberFormatException e) {
                            Log.e("MyApp", "Invalid Number", e);
                        }

                        // Start the loop to show the dialog multiple times
                        showPalletInfoDialog(0);
                    }
                });

        AlertDialog alert = getNum.create();
        alert.show();
    }

    private void showPalletInfoDialog(final int insert) {
        if (insert >= unique_Units) {
            return; // Exit the loop when all units are processed
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.custom_layout, null);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Pallet Information");

        final EditText companyEditText = promptView.findViewById(R.id.Company);
        final EditText supplierEditText = promptView.findViewById(R.id.Supplier);
        final EditText costEditText = promptView.findViewById(R.id.TotalNumber_Price);
        final EditText totalEditText = promptView.findViewById(R.id.Total_Units);
        final EditText Names = promptView.findViewById(R.id.Unit_Names);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String company = companyEditText.getText().toString();
                        String supplier = supplierEditText.getText().toString();
                        String Unit_Price = costEditText.getText().toString();
                        String Unit_Total = totalEditText.getText().toString();
                        String UnitName = Names.getText().toString();

                        // Check if the input fields are not empty before parsing
                        if (!Unit_Price.isEmpty() && !Unit_Total.isEmpty()) {
                            try {
                                int UPS = Integer.parseInt(Unit_Price);
                                int UTS = Integer.parseInt(Unit_Total);

                                dbHelper.insertData(RandNum, company, supplier, UnitName, UTS, UPS);

                                // Show the next dialog
                                showPalletInfoDialog(insert + 1);
                            } catch (NumberFormatException e) {
                                Log.e("MyApp", "Invalid Number", e);
                            }
                        } else {
                            Log.e("MyApp", "Input fields cannot be empty");
                        }
                    }
                });

        AlertDialog firstAlert = alertDialogBuilder.create();
        firstAlert.show();
    }


    private void createNumberedButtons(int count) {
        Button button = new Button(this);
        for (int i = count - 1; i < count; i++) {

            button.setText(String.valueOf(i));
            button.setId(View.generateViewId()); // Set a unique ID for each button (optional)
            button.setTag(androidx.leanback.R.id.button,RandNum);
            button.setTag(androidx.leanback.R.id.button, count);
            // Set an onClickListener for each button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = (int) button.getTag(1);
                    int y = (int) button.getTag(2);

                    showDataForButton(x , y);
                }
            });

            // Add the button to the vertical layout
            verticalLayout.addView(button);
        }
    }
    private void showDataForButton(int buttonNumber, int bTag2) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //show layout in scroll view first
        ScrollView scrollView = findViewById(R.id.scrollView2);
        scrollView.setVisibility(View.VISIBLE);

        LinearLayout mainLayout = findViewById(R.id.Main_Layout);

        int showedNum = 0;

        // Query your database based on the button number; also used to get the number of rows in the database with the same name
        Cursor cursor = db.rawQuery("SELECT * FROM my_table WHERE _id = ?", new String[]{String.valueOf(buttonNumber)});
        int totalRows = cursor.getCount();
        Object tagNum = bTag2;
        if(tagNum instanceof Integer){
            showedNum = (Integer) tagNum;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View dRetrive = inflater.inflate(R.layout.data_get_from_db_custom_layout, null);





        String company = "";
        String supplier = "";

        if(cursor != null && cursor.moveToFirst()){

            int companyIndex = cursor.getColumnIndex("company");
            int supplierIndex = cursor.getColumnIndex("supplier");

            if(companyIndex != -1 && supplierIndex != -1){
                company = cursor.getString(companyIndex);
                supplier = cursor.getString(supplierIndex);

            }

        }

        AlertDialog.Builder retriveData = new AlertDialog.Builder(this);
        retriveData.setTitle("Button Number: " + showedNum);

        //populate the scroll layout and the company selection with data from the database; the 'OK' will just dismiss the current dialogue box

        EditText Company = dRetrive.findViewById(R.id.Company_Name_Here);
        EditText Supplier = dRetrive.findViewById(R.id.Supplier_Name_Here);




        Company.setText(company);
        Supplier.setText(supplier);
        for (int i = 0; i < totalRows; i++) {

            // Create a button for each row
            String unitName = String.valueOf(cursor.getColumnIndex("unitsName"));
            Button dataPoint = new Button(this);
            dataPoint.setText(unitName);

            int getID = cursor.getColumnIndex("_id");
            dataPoint.setTag(1,i);

            mainLayout.addView(dataPoint);

            dataPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int buttonnum = 0;
                    buttonnum = (int) dataPoint.getTag(1);
                    retriveData(view, getID, buttonnum);

                }
            });





        }

        retriveData.setPositiveButton("OK",null);


        retriveData.setView(dRetrive);
        retriveData.show();
    }
    //get the buttons number to retrive the button tag between methods


    public void retriveData (View view, int I, int rowSelect){
        AlertDialog.Builder update = new AlertDialog.Builder(this);
        update.setTitle("Update Pallet");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        View dialogueView = LayoutInflater.from(this).inflate(R.layout.custom_layout, null);

        EditText Name = dialogueView.findViewById(R.id.Unit_Names);
        EditText NumUnits = dialogueView.findViewById(R.id.Total_Units);
        EditText TotalPrice = dialogueView.findViewById(R.id.TotalNumber_Price);

        Cursor cursor = db.rawQuery("SELECT * FROM my_table WHERE _id = ?", new String[]{String.valueOf(I)});
        if(cursor != null && cursor.moveToPosition(I - 1)){
            String Name1 = String.valueOf(cursor.getColumnIndex("unitsName"));
            int NumUnits1 = cursor.getColumnIndex("units");
            int TotalPrice1 = cursor.getColumnIndex("cost");
            Name.setText(Name1);
            NumUnits.setText(NumUnits1);
            TotalPrice.setText(TotalPrice1);
        }


        update.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String UpdateName = Name.getText().toString();
                String UpdateNum = NumUnits.getText().toString();
                String UpdateTotal = TotalPrice.getText().toString();

                int IntNum = Integer.parseInt(UpdateNum);
                int IntTotal = Integer.parseInt(UpdateTotal);

                dbHelper.updateData(I, UpdateName, IntNum, IntTotal);


                AlertDialog updater = update.create();
                updater.show();
            }
        });





    }

}


