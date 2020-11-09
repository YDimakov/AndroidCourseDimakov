package com.example.weather.viewModel;

import android.app.Application;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.database.DataBaseWeather;
import com.example.weather.database.DataBaseWeatherNow;
import com.example.weather.model.Weather;

import java.util.ArrayList;

public class WeatherViewModel extends ViewModel {
    private Application application;
    private MutableLiveData<ArrayList<Weather>> dataArrayList;
    private MutableLiveData<String[]> dataString;

    private String city;
    private String units;
    private ArrayList<Weather> weathersArray;
    private String cityNow;
    private String temperatureNow;
    private String[] weather;
    private final DataBaseWeather dataBaseWeather = new DataBaseWeather();
    private DataBaseWeatherNow dataBaseWeatherNow = new DataBaseWeatherNow();


    public WeatherViewModel(Application application, String city, String units
            , ArrayList<Weather> weathersArray) {
        this.application = application;
        this.city = city;
        this.units = units;
        this.weathersArray = weathersArray;
    }

    public WeatherViewModel(Application application, String city, String units) {
        this.application = application;
        this.city = city;
        this.units = units;
    }

    public LiveData<ArrayList<Weather>> getLiveDataArrayList() {
        return dataWeather();
    }

    public LiveData<String[]> getLiveDataString() {
        return getWeatherNow();
    }


    private MutableLiveData<ArrayList<Weather>> dataWeather() {
        if (dataArrayList == null) {
            dataArrayList = new MutableLiveData<>();
        }
        dataBaseWeather.getWeatherNow(city, units, weathersArray);
        dataArrayList.postValue(weathersArray);
        return dataArrayList;
    }


    private LiveData<String[]> getWeatherNow() {
        dataBaseWeatherNow.getWeatherNow(city, units);
        if (dataString == null) {
            dataString = new MutableLiveData<>();
        }
        dataBaseWeatherNow.setListenerWeatherNow(msg -> {
            temperatureNow = msg[1];
            cityNow = msg[2];
            weather = new String[]{temperatureNow, cityNow};
            dataString.postValue(weather);
        });
        return dataString;
    }
}
