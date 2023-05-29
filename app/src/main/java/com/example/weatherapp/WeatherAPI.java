package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=38d9281158448920426afe0b792745f2&units=metric")
    Call<OpenWeatherMap> getWeaterLocation(@Query("lat") double lat, @Query("lon") double lon);

    @GET("weather?appid=38d9281158448920426afe0b792745f2&units=metric")
    Call<OpenWeatherMap> getWeaterCity(@Query("q") String name);

}
