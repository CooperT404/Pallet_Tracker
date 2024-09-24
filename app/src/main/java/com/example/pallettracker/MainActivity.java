package com.example.pallettracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private LinearLayout verticalLayout;
    private int total_Pallets = 1;
    private int unique_Units = 0;
    private int RandNum = 0;
    private int Max = 99999;
    private int Min = 10000;
    private Context context;
    private String[] fileNames = {"copyrightpolicy.txt", "eula.txt", "privacypolicy.txt", "tacfos.txt"};
    private int currentFileIndex = 0;


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
                createButtonsWithTags();
                showInputDialog();
            }
        });
        createButtonsWithTags();
        showNextDialog();
    }



    protected void showInputDialog() {


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



    private void createButtonsWithTags() {
        List<String> uniqueIDs = dbHelper.getUniqueIDs();

        for (String id : uniqueIDs) {
            Button button = new Button(this);
            button.setText(id);
            button.setTag(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) v.getTag();
                    List<String> IDs = dbHelper.getIDsByTag(tag);

                    // Create a new AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("New Buttons");

                    // Create a LinearLayout to hold the new buttons
                    LinearLayout dialogLayout = new LinearLayout(MainActivity.this);
                    dialogLayout.setOrientation(LinearLayout.VERTICAL);

                    // Initialize a counter for the new button tags
                    int counter = 1;

                    for (String newID : IDs) {
                        Button newButton = new Button(MainActivity.this);
                        newButton.setText(newID);
                        newButton.setTag(counter); // Set the tag to the counter value
                        int finalCounter = counter;
                        newButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showCustomDialog(Integer.parseInt(newID),finalCounter); // Use the counter value
                            }
                        });
                        dialogLayout.addView(newButton);
                        counter++; // Increment the counter for the next button
                    }

                    // Set the LinearLayout as the dialog's view
                    builder.setView(dialogLayout);

                    // Add a button to close the dialog
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Show the dialog
                    builder.show();
                }
            });
            verticalLayout.addView(button);
        }
    }



    private void showCustomDialog(int tag, int oTag) {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.data_get_from_db_custom_layout, null);

        // Use dialogView.findViewById to reference the EditText fields within the custom layout
        EditText company = dialogView.findViewById(R.id.editText1);
        EditText supplier = dialogView.findViewById(R.id.editText2);
        EditText unitName = dialogView.findViewById(R.id.editText3);
        EditText unitPrice = dialogView.findViewById(R.id.editText4);
        EditText units = dialogView.findViewById(R.id.editText5);

        // Log the EditText references to ensure they are not null
        Log.d("Dialog", "company EditText: " + (company != null));
        Log.d("Dialog", "supplier EditText: " + (supplier != null));
        Log.d("Dialog", "unitName EditText: " + (unitName != null));
        Log.d("Dialog", "unitPrice EditText: " + (unitPrice != null));
        Log.d("Dialog", "units EditText: " + (units != null));

        // Retrieve and log the data from the database
        String companyData = dbHelper.getStringFromDatabase(tag, MyDatabaseHelper.COLUMN_COMPANY, oTag);
        String supplierData = dbHelper.getStringFromDatabase(tag, "supplier",oTag);
        String unitNameData = dbHelper.getStringFromDatabase(tag, "unitsName", oTag);
        String unitPriceData = dbHelper.getStringFromDatabase(tag, "cost", oTag);
        String unitsData = dbHelper.getStringFromDatabase(tag, "units", oTag);

        Log.d("Database", "companyData: " + companyData);
        Log.d("Database", "supplierData: " + supplierData);
        Log.d("Database", "unitNameData: " + unitNameData);
        Log.d("Database", "unitPriceData: " + unitPriceData);
        Log.d("Database", "unitsData: " + unitsData);

        // Set the text for the EditText fields
        company.setText(companyData);
        supplier.setText(supplierData);
        unitName.setText(unitNameData);
        unitPrice.setText(unitPriceData);
        units.setText(unitsData);

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tag: " + tag);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comFinal = company.getText().toString();
                String supFinal = supplier.getText().toString();
                String unitNameFinal = unitName.getText().toString();
                String unitPriceFinal = unitPrice.getText().toString();
                String unitsFinal = units.getText().toString();

                dbHelper.updateData(tag, oTag, unitNameFinal, unitsFinal, supFinal, unitPriceFinal, comFinal);


                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void showNextDialog() {
        if (currentFileIndex < fileNames.length) {
            String fileName = fileNames[currentFileIndex];
            String fileContent = readFileFromAssets(fileName);

            // Log the file content to verify it is being read correctly
            Log.d("FileContent", fileContent);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Document " + (currentFileIndex + 1));

            ScrollView scrollView = new ScrollView(this);
            TextView textView = new TextView(this);
            textView.setText(fileContent);

            // Set text size and padding for better visibility
            textView.setTextSize(16);
            textView.setPadding(16, 16, 16, 16);

            scrollView.addView(textView);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText("I have read and understood this document");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(scrollView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
            layout.addView(checkBox, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            builder.setView(layout);

            builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (checkBox.isChecked()) {
                        currentFileIndex++;
                        showNextDialog();
                    } else {
                        // Redisplay the last message
                        showNextDialog();
                    }
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        } else {
            // All documents have been shown
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("All documents have been read.");
            builder.setPositiveButton("OK", null);
            builder.show();
        }
    }

    private String readFileFromAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }




}


