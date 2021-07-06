package com.example.weather.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.data.AdapterWeather;
import com.example.weather.factory.MainFactoryWeather;
import com.example.weather.factory.MainFactoryWeatherNow;
import com.example.weather.model.Weather;
import com.example.weather.viewModel.WeatherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.annotations.NonNull;

import static com.example.weather.activity.SelectCity.APP_PREFERENCES_CITY;
import static com.example.weather.activity.SettingsActivity.APP_PREFERENCES;
import static com.example.weather.activity.SettingsActivity.APP_PREFERENCES_VARIABLE;


public class MainActivity extends AppCompatActivity {
    public static final String API_KEY = "06645db764871aff86dd6e3e75efecbc";
    public static final String API_KEY_NOW = "66cd4ee518afb5c50acfc9cbf2920994";
    public static final String API_UNITS_MODE_IMPERIAL = "imperial";
    public static final String API_UNITS_MODE_METRIC = "metric";
    public static final String LOG_TAG = "tag";
    private AdapterWeather adapterWeather;
    private ArrayList<Weather> weathersArray;
    private TextView cityWeatherToday;
    private TextView tempWeatherToday;
    private TextView timeNow;
    private WeatherViewModel viewModelWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String city = mSettings.getString(APP_PREFERENCES_CITY, "Kiev");
        weathersArray = new ArrayList<>();
        timeNow = findViewById(R.id.timeNow);

        cityWeatherToday = findViewById(R.id.cityWeatherToday);
        tempWeatherToday = findViewById(R.id.tempWeatherToday);
        int varTempWeather = mSettings.getInt(APP_PREFERENCES_VARIABLE, 0);

        if (varTempWeather == 1) {
            if (city != null) {
                viewModelWeather = new ViewModelProvider(this // данные с одного API
                        , new MainFactoryWeather(new Application(), city, API_UNITS_MODE_IMPERIAL, weathersArray))
                        .get(WeatherViewModel.class);
                viewModelWeather.getLiveDataArrayList()
                        .observe(this, weathers ->
                                weathersArray = weathers);
                viewModelWeather = new ViewModelProvider// данные с другого API
                        (this, new MainFactoryWeatherNow(new Application(), city,
                                API_UNITS_MODE_IMPERIAL)).get(WeatherViewModel.class);

                viewModelWeather.getLiveDataString().observe(this, strings -> {
                    tempWeatherToday.setText(strings[0] + "F ");
                    cityWeatherToday.setText(strings[1]);


                });
            }
        } else {
            if (city != null) {
                viewModelWeather = new ViewModelProvider(this// данные с одного API
                        , new MainFactoryWeather(new Application(), city, API_UNITS_MODE_METRIC, weathersArray))
                        .get(WeatherViewModel.class);
                viewModelWeather.getLiveDataArrayList()
                        .observe(this, weathers -> weathersArray = weathers);

                viewModelWeather = new ViewModelProvider// данные с другого API
                        (this, new MainFactoryWeatherNow(new Application(), city
                                , API_UNITS_MODE_METRIC)).get(WeatherViewModel.class);

                viewModelWeather.getLiveDataString().observe(this, strings -> {
                    tempWeatherToday.setText(strings[0] + "°C ");
                    cityWeatherToday.setText(strings[1]);
                });
            }
        }

        initRecyclerView();
        time();
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> startActivitySelectCity());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        adapterWeather.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivitySettings();
        } else if (item.getItemId() == R.id.refresh) {
            adapterWeather.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivitySettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void startActivitySelectCity() {
        Intent intent = new Intent(this, SelectCity.class);
        startActivity(intent);
        finish();
    }

    void initRecyclerView() {
        RecyclerView recyclerViewWeather = findViewById(R.id.recyclerView);
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapterWeather = new AdapterWeather(this, weathersArray);
        recyclerViewWeather.setAdapter(adapterWeather);
    }

    private void time() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");
        timeNow.setText("Last updated " + formatForDateNow.format(dateNow));
    }

}
