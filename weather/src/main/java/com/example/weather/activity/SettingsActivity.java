package com.example.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_VARIABLE = "variable";
    private SharedPreferences mSettings;
    int variable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_settings);
        Switch aSwitch = findViewById(R.id.switchTemp);
        aSwitch.setOnCheckedChangeListener(this);
        int varSwitch = mSettings.getInt(APP_PREFERENCES_VARIABLE, 0);
        if (varSwitch == 1) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }
        Button buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            variable = 0;
        } else {
            variable = 1;
        }
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_VARIABLE, variable);
        editor.apply();

        Toast.makeText(this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
    }
}
