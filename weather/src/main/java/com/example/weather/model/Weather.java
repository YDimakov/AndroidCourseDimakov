package com.example.weather.model;


public class Weather {
    private String date;
    private String temperature;
    private String mainWeather;
    private int description;

    public Weather(String date, String temperature, String mainWeather, int description) {
        this.date = date;
        this.temperature = temperature;
        this.mainWeather = mainWeather;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temperature;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public int getDescription() {
        return description;
    }
}

