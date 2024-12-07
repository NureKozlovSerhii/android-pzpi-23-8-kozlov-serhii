package com.example.serhii_kozlov_pzpi_23_8;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Пошук RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Встановлення LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Створення списку даних
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            items.add("Item " + i);
        }

        // Налаштування адаптера
        MyAdapter adapter = new MyAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}