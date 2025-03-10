package com.example.wth_app.client.web;

import com.example.wth_app.dto.AirQualityResponse;
import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;

public interface WebWeatherClient {
    WeatherResponse getWeather(String city, String apiKey);

    WeatherResponse getWeather(double latitude, double longitude, String lang);

    AirQualityResponse getAirQuality(double latitude, double longitude);

    GeoLocation getCoordinates(String city);
}
