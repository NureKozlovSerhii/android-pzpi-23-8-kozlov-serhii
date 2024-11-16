package com.example.kozlov_serhii_pzpi_23_8;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Панель кольору, яка змінює фон
    private View colorPanel;
    // Регулятори кольору RGB
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    // Основний макет і контейнер для регуляторів
    private LinearLayout mainLayout, seekbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізація елементів інтерфейсу
        colorPanel = findViewById(R.id.color_panel);
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);
        mainLayout = findViewById(R.id.main_layout);
        seekbarContainer = findViewById(R.id.seekbar_container);

        // Налаштування макета в залежності від орієнтації
        configureLayoutForOrientation();

        // Лістенер для відстеження змін на регуляторах кольору
        SeekBar.OnSeekBarChangeListener colorChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Оновлення кольору панелі при зміні значення
                updateColorPanel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Викликається, коли користувач починає взаємодію з регулятором БУДЕ ПОМИЛКА ЯКЩО НЕ ЗМІНИТИ
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Викликається, коли користувач завершує взаємодію з регулятором БУДЕ ПОМИЛКА ЯКЩО НЕ ЗМІНИТИ
            }
        };

        // Додавання лістенера до кожного регулятора
        seekBarRed.setOnSeekBarChangeListener(colorChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(colorChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(colorChangeListener);

        // Початкове оновлення панелі кольору
        updateColorPanel();
    }

    // Метод для налаштування макета відповідно до орієнтації екрана
    private void configureLayoutForOrientation() {
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Якщо екран у ландшафтній орієнтації
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            colorPanel.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
            seekbarContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        } else {
            // Якщо екран у портретній орієнтації
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            colorPanel.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 2));
            seekbarContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        }
    }

    // Метод для оновлення фону панелі кольору
    private void updateColorPanel() {
        // Отримання значень RGB з регуляторів
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();

        // Формування кольору за допомогою Color.rgb()
        int color = Color.rgb(red, green, blue);

        // Встановлення нового кольору фону панелі
        colorPanel.setBackgroundColor(color);
    }
}
