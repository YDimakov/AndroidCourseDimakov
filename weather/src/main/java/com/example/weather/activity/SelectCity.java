package com.example.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather.R;

import static com.example.weather.activity.SettingsActivity.APP_PREFERENCES;

public class SelectCity extends AppCompatActivity {
    public static final String APP_PREFERENCES_CITY = "city";
    private SharedPreferences sharedPreferences;
    private EditText textCity;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_weather);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ImageButton imageButtonCheck = findViewById(R.id.buttonCheckSelectCity);
        ImageButton imageButtonClear = findViewById(R.id.buttonClearSelectCity);
        textCity = findViewById(R.id.editTextCity);
        imageButtonCheck.setOnClickListener(v -> {
            startActivity();
        });
        imageButtonClear.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void startActivity() {
        String city = textCity.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES_CITY, city);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
