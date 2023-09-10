package com.example.pallettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kotlin.jvm.internal.markers.KMutableList;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout Pallet_Info_ButtonsList = findViewById(R.id.Pallet_Info_ButtonsList);


        //Text for the pallet in preview
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        //Creating new instances for differing pallets
        // Click Button...

        Button CNP = (Button) findViewById(R.id.New_Pallet);
        CNP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Button newButton = new Button(MainActivity.this);
                newButton.setText("New Pallet");
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Prompt_Pallet_Info.class);
                        startActivity(intent);
                    }

                });


                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.height = 150;
                layoutParams.width =1000;
                layoutParams.gravity = Gravity.CENTER;

                newButton.setLayoutParams(layoutParams);



                Pallet_Info_ButtonsList.addView(newButton);

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



}