package com.example.wth_app.service.web;

import com.example.wth_app.model.dto.WeatherResponse;
import com.example.wth_app.model.dto.WeatherResponseDTO;

public interface WebWeatherService {
    WeatherResponse getWeather(double latitude, double longitude, String lang);

    WeatherResponseDTO getWeatherByCity(String city, String lang);

    void getAndSaveWeatherDataByCity(String city);

    String getWeatherHtmlPageByCity(String city, String lang);
}
