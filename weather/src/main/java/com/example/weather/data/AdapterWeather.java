package com.example.weather.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.R;
import com.example.weather.model.Weather;

import java.util.ArrayList;

public class AdapterWeather extends RecyclerView.Adapter<AdapterWeather.WeatherHolder> {

    private Context context;
    private ArrayList<Weather> listWeather;

    public AdapterWeather(Context context, ArrayList<Weather> listWeather) {
        this.context = context;
        this.listWeather = listWeather;
    }

    public class WeatherHolder extends RecyclerView.ViewHolder {

        ImageView imageViewWeatherModel;
        TextView textViewDateModel;
        TextView textViewTemperatureModel;
        TextView textViewWeatherTimeModel;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);
            imageViewWeatherModel = itemView.findViewById(R.id.imageViewWeatherModel);
            textViewDateModel = itemView.findViewById(R.id.textViewDateModel);
            textViewTemperatureModel = itemView.findViewById(R.id.textViewTempModel);
            textViewWeatherTimeModel = itemView.findViewById(R.id.textViewTimeModel);
        }
    }


    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_model, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        Weather weather = listWeather.get(position);
        String date = weather.getDate();
        String temperature = weather.getTemp();
        String mainWeather = weather.getMainWeather();
        int description = weather.getDescription();
        holder.imageViewWeatherModel.setImageResource(description);
        holder.textViewDateModel.setText(date);
        holder.textViewTemperatureModel.setText(temperature);
        holder.textViewWeatherTimeModel.setText(mainWeather);
    }

    @Override
    public int getItemCount() {
        return listWeather != null ? listWeather.size() : 0;
    }

}
