package com.example.wth_app.client.web;

import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;

public interface WeatherClient {
    WeatherResponse getWeather(String city, String apiKey);

    WeatherResponse getWeather(double latitude, double longitude, String lang);

    GeoLocation getCoordinates(String city);
}
