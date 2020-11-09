package com.example.weather.database;

import android.util.Log;

import com.example.weather.interfaceWeather.ListenerWeatherNow;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.weather.activity.MainActivity.API_KEY_NOW;
import static com.example.weather.activity.MainActivity.LOG_TAG;

public class DataBaseWeatherNow {

    private String weatherIconNow;
    private String cityNow;
    private String temperatureNow;
    private String[] weather;
    private ListenerWeatherNow listenerWeatherNow;

    public void setListenerWeatherNow(ListenerWeatherNow listenerWeatherNow) {
        this.listenerWeatherNow = listenerWeatherNow;
    }

    public String[] getWeatherNow(String city, String units) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=" + units + "&appid=" + API_KEY_NOW;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        weatherIconNow = json.getJSONArray("weather").getJSONObject(0).getString("icon");
                        temperatureNow = json.getJSONObject("main").getString("temp");
                        cityNow = json.getString("name");
                        weather = new String[]{weatherIconNow, temperatureNow, cityNow};
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (listenerWeatherNow != null) {
                        listenerWeatherNow.getWeatherNow(weather);
                        listenerWeatherNow =null;
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(LOG_TAG, "onFailureWeatherNow");
            }

        });

        return weather;
    }
}

