package com.example.pallettracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

public class Calculator extends MainActivity {
    int temp = 0;
    double Array[];
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        //calculator buttons
        Button Button_9 = (Button) findViewById(R.id.Num9);
        Button Button_8 = (Button) findViewById(R.id.Num8);
        Button Button_7 = (Button) findViewById(R.id.Num7);
        Button Button_6 = (Button) findViewById(R.id.Num6);
        Button Button_5 = (Button) findViewById(R.id.Num5);
        Button Button_4 = (Button) findViewById(R.id.Num4);
        Button Button_3 = (Button) findViewById(R.id.Num3);
        Button Button_2 = (Button) findViewById(R.id.Num2);
        Button Button_1 = (Button) findViewById(R.id.Num1);
        Button Button_Add = (Button) findViewById(R.id.button_Add);
        Button Button_Sub = (Button) findViewById(R.id.button_Sub);
        Button Button_Div = (Button) findViewById(R.id.button_Div);
        Button Button_Multi = (Button) findViewById(R.id.button_Multi);
        Button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 9;
            }
        });
        Button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 8;
            }
        });
        Button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 7;
            }
        });
        Button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 6;
            }
        });
        Button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 5;
            }
        });
        Button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 4;
            }
        });
        Button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 3;
            }
        });
        Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 2;
            }
        });
        Button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = (temp * 10) + 1;
            }
        });
        Button_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Array[i] = temp;
                ++i;
                Array[i] = '+';
            }
        });
        Button_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Array[i] = temp;
                ++i;
                Array[i] = '-';
            }
        });
        Button_Div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Array[i] = temp;
                ++i;
                Array[i] = '/';
            }
        });
        Button_Multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Array[i] = temp;
                ++i;
                Array[i] = '*';
            }
        });
        //nav Buttons
        Button inventory = (Button) findViewById(R.id.b2_To_Pinven);
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_calc = new Intent(Calculator.this, MainActivity.class);
                startActivity(intent_calc);
            }
        });
        Button notes = (Button) findViewById(R.id.b2_To_Notes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Calculator.this, Prompt_Pallet_Info.class);
//                startActivity(intent);
            }
        });
        Button archive = (Button) findViewById(R.id.b2_To_Archive);
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Calculator.this, Prompt_Pallet_Info.class);
//                startActivity(intent);
            }
        });
    }
}
