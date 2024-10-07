package com.example.serhii_kozlov_pzpi_23_8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button myButton = findViewById(R.id.my_button);


        TextView myTextView = findViewById(R.id.my_textview);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myTextView.setText("Текст змінено!");
            }
        });
    }
}