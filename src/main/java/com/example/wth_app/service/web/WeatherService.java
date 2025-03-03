package com.example.wth_app.service.web;

import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;

public interface WeatherService {
    WeatherResponse getWeather(double latitude, double longitude, String lang);

    WeatherResponseDTO getWeatherByCity(String city, String lang);

    void saveWeatherData(String city);

    String getWeatherReport(String city);

    String getHtmlWeatherByCity(String city, String lang);
}
