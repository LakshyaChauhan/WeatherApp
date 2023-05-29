package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private TextView cityWeather,tempWeather,conditionWeather,humidityWeather,maxTempWeather,minTempWeather,windWeather,pressureWeather;
    private ImageView imageViewWeather;
    private Button search;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityWeather = findViewById(R.id.textViewCityWeather);
        tempWeather= findViewById(R.id.textViewTempWeather);
        conditionWeather = findViewById(R.id.WeatherCOnditionWeather);
        humidityWeather = findViewById(R.id.textViewHumidityWeather);
        maxTempWeather = findViewById(R.id.textViewMaxTempWeather);
        minTempWeather = findViewById(R.id.textViewMinTempWeather);
        windWeather = findViewById(R.id.textViewWindWeather);
        pressureWeather= findViewById(R.id.textViewPressureWeather);

        imageViewWeather = findViewById(R.id.imageViewWeater);

        search = findViewById(R.id.search);

        editText = findViewById(R.id.editTextCityName);

        getWeatherDatea("Chandigarh");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName =editText.getText().toString();

                getWeatherDatea(cityName);

                editText.setText("");
            }
        });

    }


    public void getWeatherDatea(String name){
        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call =weatherAPI.getWeaterCity(name);

        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {

                if(response.isSuccessful()) {
                    cityWeather.setText(response.body().getName() + " , " + response.body().getSys().getCountry());
                    tempWeather.setText(response.body().getMain().getTemp() + " °C");
                    conditionWeather.setText(response.body().getWeather().get(0).getDescription());
                    humidityWeather.setText(response.body().getMain().getHumidity() + "%");
                    maxTempWeather.setText(response.body().getMain().getTempMax() + " °C");
                    minTempWeather.setText(response.body().getMain().getTempMin() + " °C");
                    pressureWeather.setText(response.body().getMain().getPressure() + "");
                    windWeather.setText(response.body().getWind().getSpeed() + "s");

                    String iconCode = response.body().getWeather().get(0).getIcon();
                    Picasso.get().load("https://openweathermap.org/img/wn/" + iconCode + "@2x.png")
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageViewWeather);
                }else{
                    Toast.makeText(WeatherActivity.this,"City not found please try again",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

            }
        });
    }

}