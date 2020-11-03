package com.example.weather.viewModel;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.weather.activity.MainActivity.API_KEY_NOW;
import static com.example.weather.activity.MainActivity.LOG_TAG;

public class WeatherNowViewModel extends ViewModel {
    private Application application;
    private MutableLiveData<String[]> data;
    private String weatherIconNow;
    private String cityNow;
    private String temperatureNow;
    private String city;
    private String units;
    private String[] weather;

    public WeatherNowViewModel(Application application, String city, String units) {
        this.application = application;
        this.city = city;
        this.units = units;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<String[]> getWeatherNow() {

        if (data == null) {
            data = new MutableLiveData<>();
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=" + units + "&appid=" + API_KEY_NOW;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(LOG_TAG, "onFailureWeatherNow");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        weatherIconNow = json.getJSONArray("weather").getJSONObject(0).getString("icon");
                        temperatureNow = json.getJSONObject("main").getString("temp");
                        cityNow = json.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            weather = new String[]{weatherIconNow, temperatureNow, cityNow};
            data.postValue(weather);
        });
        return data;
    }
}
