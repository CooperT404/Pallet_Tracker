package com.example.pallettracker;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class Prompt_Pallet_Info extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] compaines = {"Amazon", "Cummins", "Apple"};
    String[] Suppliers = {"Lake Erie Liquidators", "Company A", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_pallet_info);
         Button Create_Pallet = (Button) findViewById(R.id.Create_Pallet_Button);
        Create_Pallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button back = (Button) findViewById(R.id.BackB_PalletInventory);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Prompt_Pallet_Info.this, MainActivity.class);
                startActivity(intent);

            }
        });
        //tells you which spinner is selected
        Spinner spino2 = findViewById(R.id.Supplier_Spinner);
        Spinner spino = findViewById(R.id.Man_List);
        spino.setOnItemSelectedListener(this);
        spino2.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, compaines);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Suppliers);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino2.setAdapter(ad2);
        spino.setAdapter(ad);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), compaines[adapterView.getFirstVisiblePosition()], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
