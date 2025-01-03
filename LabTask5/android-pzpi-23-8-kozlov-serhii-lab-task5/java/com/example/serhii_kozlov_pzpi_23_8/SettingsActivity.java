package com.example.serhii_kozlov_pzpi_23_8;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private RadioGroup themeGroup;
    private SeekBar fontSizeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        themeGroup = findViewById(R.id.themeGroup);
        fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);

        int currentTheme = preferences.getInt("theme", 0);
        int fontSize = preferences.getInt("fontSize", 14);

        themeGroup.check(currentTheme == 0 ? R.id.themeLight : R.id.themeDark);

        fontSizeSeekBar.setProgress(fontSize - 12);

        themeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int theme = checkedId == R.id.themeLight ? 0 : 1;
            preferences.edit().putInt("theme", theme).apply();

            recreate();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSize = progress + 12;
                preferences.edit().putInt("fontSize", fontSize).apply();

                TextView fontSizeValue = findViewById(R.id.fontSizeValue);
                fontSizeValue.setText(fontSize + "sp");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void applyTheme() {
        int theme = preferences.getInt("theme", 0);
        if (theme == 0) {
            setTheme(R.style.Theme_Light);
        } else {
            setTheme(R.style.Theme_Dark);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

