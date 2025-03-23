package com.example.wth_app.client.web;

import com.example.wth_app.model.dto.AirQualityResponse;
import com.example.wth_app.model.dto.GeoLocation;
import com.example.wth_app.model.dto.WeatherResponse;

public interface WebWeatherClient {
    WeatherResponse getWeather(String city, String apiKey);

    WeatherResponse getWeather(double latitude, double longitude, String lang);

    AirQualityResponse getAirQuality(double latitude, double longitude);

    GeoLocation getCoordinates(String city);
}
