package com.example.serhii_kozlov_pzpi_23_8;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showDialogButton = findViewById(R.id.showDialogButton);
        Button showDatePickerButton = findViewById(R.id.showDatePickerButton);
        Button showCustomDialogButton = findViewById(R.id.showCustomDialogButton);

        Button goToNewPageButton = findViewById(R.id.goToNewPageButton);

        Button goToTask3 = findViewById(R.id.goToTask3);

        goToTask3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        });

        goToNewPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // AlertDialog
        showDialogButton.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Alert Dialog")
                    .setMessage("This is an example of AlertDialog.")
                    .setPositiveButton("OK", (dialog, which) ->
                            Toast.makeText(MainActivity.this, "OK clicked", Toast.LENGTH_SHORT).show())
                    .setNegativeButton("Cancel", (dialog, which) ->
                            Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show())
                    .show();
        });

        // DatePickerDialog
        showDatePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                            Toast.makeText(MainActivity.this,
                                    "Selected date: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear,
                                    Toast.LENGTH_SHORT).show(),
                    year, month, day);

            datePickerDialog.show();
        });

        // Custom Dialog
        showCustomDialogButton.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog, null);

            EditText inputField = dialogView.findViewById(R.id.customDialogInput);

            new AlertDialog.Builder(MainActivity.this)
                    .setView(dialogView)
                    .setPositiveButton("OK", (dialog, id) ->
                            Toast.makeText(MainActivity.this,
                                    "Input: " + inputField.getText().toString(),
                                    Toast.LENGTH_SHORT).show())
                    .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss())
                    .create()
                    .show();
        });
    }
}