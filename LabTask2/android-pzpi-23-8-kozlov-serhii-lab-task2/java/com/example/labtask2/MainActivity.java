package com.example.labtask2;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View colorPanel;
    private SeekBar seekBarRed, seekBarGreen, seekBarBlue;
    private LinearLayout mainLayout, seekbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorPanel = findViewById(R.id.color_panel);
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);
        mainLayout = findViewById(R.id.main_layout);
        seekbarContainer = findViewById(R.id.seekbar_container);

        configureLayoutForOrientation();

        SeekBar.OnSeekBarChangeListener colorChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColorPanel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };

        seekBarRed.setOnSeekBarChangeListener(colorChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(colorChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(colorChangeListener);

        updateColorPanel();
    }

    private void configureLayoutForOrientation() {
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            colorPanel.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
            seekbarContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        } else {
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            colorPanel.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 2));
            seekbarContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        }
    }

    private void updateColorPanel() {
        int red = seekBarRed.getProgress();
        int green = seekBarGreen.getProgress();
        int blue = seekBarBlue.getProgress();
        int color = Color.rgb(red, green, blue);
        colorPanel.setBackgroundColor(color);
    }
}