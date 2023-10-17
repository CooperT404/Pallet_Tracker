package com.example.pallettracker;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Prompt_Pallet_Info {

    private int buttonCount = 0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyButtons", Context.MODE_PRIVATE);

        final LinearLayout Pallet_Info_ButtonsList = findViewById(R.id.Pallet_Info_ButtonsList);
        loadData(Pallet_Info_ButtonsList);
        //Text for the pallet in preview
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Creating new instances for differing pallets
        // Click Button...

        Button CNP = (Button) findViewById(R.id.New_Pallet);
        CNP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                buttonCount++;
                Button newButton = new Button(MainActivity.this);
                newButton.setText("New Pallet" + buttonCount);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.height = 150;
                layoutParams.width =1000;
                layoutParams.gravity = Gravity.CENTER;

                newButton.setLayoutParams(layoutParams);
                Pallet_Info_ButtonsList.addView(newButton);
                saveData();
            }
        });


        //Nav Buttons too different pages

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



    }


    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("buttonCount", buttonCount);
        editor.apply();
    }

    private void loadData(LinearLayout layout) {
        buttonCount = sharedPreferences.getInt("buttonCount", 0);
        for (int i = 1; i <= buttonCount; i++) {
            Button newButton = new Button(MainActivity.this);
            newButton.setText("New Pallet " + i);
            newButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Prompt_Pallet_Info.class);
                    startActivity(intent);
                }

            });
            layout.addView(newButton);
        }
    }
}