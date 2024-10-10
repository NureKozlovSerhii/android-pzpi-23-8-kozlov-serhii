package com.example.practtask22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ПершаActivity", "onCreate called");

        findViewById(R.id.button).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
        });

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString("savedText");
            EditText editText = findViewById(R.id.editText);
            editText.setText(savedText);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ПершаActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ПершаActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ПершаActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ПершаActivity", "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ПершаActivity", "onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText editText = findViewById(R.id.editText);
        outState.putString("savedText", editText.getText().toString());
        Log.d("ПершаActivity", "onSaveInstanceState called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("ПершаActivity", "onRestoreInstanceState called");
    }
}