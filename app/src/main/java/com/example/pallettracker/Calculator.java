package com.example.pallettracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//calculator done DNT
public class Calculator extends AppCompatActivity {

    private TextView textView;
    private String currentNumber = "";
    private String currentExpression = "";
    private double result = 0;
    private char operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        textView = findViewById(R.id.textView);
    }

    public void onButtonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();


        if (buttonText.equals("=")) {
            currentExpression += currentNumber;
            calculate();
        } else {
            if (buttonText.matches("[0-9]")) {
                currentNumber += buttonText;
                currentExpression += buttonText;
                textView.setText(currentExpression);
                currentNumber = "";
            } else if (buttonText.matches("[+\\-*/]")) {
                currentExpression += buttonText;
                operator = buttonText.charAt(0);
                currentNumber = "";
                textView.setText(currentExpression);
            }
        }
    }

    private void calculate() {
        String[] parts = currentExpression.split("[-+*/]");

        if (parts.length != 2) {
            textView.setText("Error");
            return;
        }

        double num1 = Double.parseDouble(parts[0]);
        double num2 = Double.parseDouble(parts[1]);

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    textView.setText("Error");
                    return;
                }
                break;
        }

        textView.setText(Double.toString(result));
        currentExpression = Double.toString(result);
        currentNumber = "";
    }
    public void onClearClick(View v) {
        currentNumber = "";
        currentExpression = "";
        result = 0;
        operator = '\0';
        textView.setText("0");
    }

}
