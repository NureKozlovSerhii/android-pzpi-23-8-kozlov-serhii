package com.example.practtask22;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d("другаActivity", "onCreate called");

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.d("другаActivity", "finish() called - Activity is finishing");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("другаActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("другаActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("другаActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("другаActivity", "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("другаActivity", "onDestroy called");
    }
}
