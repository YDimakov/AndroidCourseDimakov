package com.example.weather.database;



import android.util.Log;



import com.example.weather.R;
import com.example.weather.model.Weather;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.weather.activity.MainActivity.API_KEY;
import static com.example.weather.activity.MainActivity.LOG_TAG;

public class DataBaseWeather {
    private String temperature;

    public void getWeatherNow(String city, String units, ArrayList<Weather> weathersArray) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=" + units + "&appid=" + API_KEY;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws
                    IOException {

                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray jsonArrayList = json.getJSONArray("list");
                        for (int i = 0; i <= jsonArrayList.length(); i++) {
                            JSONObject jsonObject = jsonArrayList.getJSONObject(i);
                            String icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                            String dateTxt = jsonObject.getString("dt_txt");
                            String month = dateTxt.substring(5, 7);
                            String day = dateTxt.substring(8, 10);
                            String time = dateTxt.substring(11, 16);
                            String data = day + "." + month;
                            if (units.equals("metric")) {
                                temperature = (jsonObject.getJSONObject("main").getString("temp")) + "°С ";
                            } else {
                                temperature = (jsonObject.getJSONObject("main").getString("temp")) + "F ";
                            }
                            switch (icon) {
                                case "01d":
                                case "01n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d01d));
                                    break;
                                case "02d":
                                case "02n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d02d));
                                    break;
                                case "03d":
                                case "03n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d03d));
                                    break;
                                case "04d":
                                case "04n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d04d));
                                    break;
                                case "09d":
                                case "09n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d09d));
                                    break;
                                case "10d":
                                case "10n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d10d));
                                    break;
                                case "11d":
                                case "11n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d11d));
                                    break;
                                case "13d":
                                case "13n":
                                    weathersArray.add(new Weather(data, temperature, time, R.drawable.d13d));
                                    break;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(LOG_TAG, "onFailureWeather");
            }
        });
    }
}

