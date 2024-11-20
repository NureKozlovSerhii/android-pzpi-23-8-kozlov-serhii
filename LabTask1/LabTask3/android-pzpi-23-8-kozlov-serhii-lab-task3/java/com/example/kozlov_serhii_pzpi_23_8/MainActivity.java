package com.example.kozlov_serhii_pzpi_23_8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber1, editTextNumber2;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        textViewResult = findViewById(R.id.textViewResult);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);

        buttonAdd.setOnClickListener(v -> calculate("+"));
        buttonSubtract.setOnClickListener(v -> calculate("-"));
        buttonMultiply.setOnClickListener(v -> calculate("*"));
        buttonDivide.setOnClickListener(v -> calculate("/"));
    }

    private void calculate(String operation) {
        String num1String = editTextNumber1.getText().toString();
        String num2String = editTextNumber2.getText().toString();

        if (num1String.isEmpty() || num2String.isEmpty()) {
            textViewResult.setText("Please enter both numbers");
            return;
        }

        double num1 = Double.parseDouble(num1String);
        double num2 = Double.parseDouble(num2String);
        double result = 0;

        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    textViewResult.setText("Cannot divide by zero");
                    return;
                }
                result = num1 / num2;
                break;
        }
        textViewResult.setText("Result: " + result);
    }
}
