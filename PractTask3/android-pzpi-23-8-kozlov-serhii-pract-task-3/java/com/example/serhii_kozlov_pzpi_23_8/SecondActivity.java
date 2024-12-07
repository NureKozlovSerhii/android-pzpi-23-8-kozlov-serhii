package com.example.serhii_kozlov_pzpi_23_8;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private Handler handler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textViewMessage);
        Button buttonStart = findViewById(R.id.buttonStartTask);

        // Ініціалізація Handler, прив'язаний до головного потоку
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    textView.setText("Task completed in background!");
                }
            }
        };

        buttonStart.setOnClickListener(v -> startBackgroundTask());
    }

    private void startBackgroundTask() {
        // Фонова задача
        new Thread(() -> {
            try {
                // Імітація тривалої операції (3 секунди)
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Надсилаємо повідомлення до головного потоку
            Message message = handler.obtainMessage();
            message.what = 1; // Код повідомлення
            handler.sendMessage(message);
        }).start();

        textView.setText("Task started...");
    }
}