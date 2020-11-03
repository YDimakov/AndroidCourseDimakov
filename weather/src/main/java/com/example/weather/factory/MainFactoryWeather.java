package com.example.weather.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.model.Weather;
import com.example.weather.viewModel.WeatherViewModel;

import java.util.ArrayList;

public class MainFactoryWeather extends ViewModelProvider.AndroidViewModelFactory {
    private Application application;
    private String city;
    private String units;
    private ArrayList<Weather> weathersArray;

    public MainFactoryWeather(@NonNull Application application, String city, String units, ArrayList<Weather> weathersArray) {
        super(application);
        this.application = application;
        this.city = city;
        this.units = units;
        this.weathersArray = weathersArray;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModel(application, city, units, weathersArray);
    }
}
