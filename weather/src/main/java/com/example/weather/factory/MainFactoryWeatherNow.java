package com.example.weather.factory;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.viewModel.WeatherViewModel;

public class MainFactoryWeatherNow extends ViewModelProvider.AndroidViewModelFactory {
    private Application application;
    private String city;
    private String units;


    public MainFactoryWeatherNow(@NonNull Application application, String city, String units) {
        super(application);
        this.application = application;
        this.city = city;
        this.units = units;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModel(application, city, units);
    }
}
