package com.example.kozlov_serhii_pzpi_23_8;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ПершаActivity", "onCreate called");
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
    protected void onRestart() {
        super.onRestart();
        Log.d("ПершаActivity", "onRestart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ПершаActivity", "onDestroy called");
    }
}