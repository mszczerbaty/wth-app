package com.example.wth_app.service;

import com.example.wth_app.client.WeatherClient;
import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.city}")
    private String city;

    public WeatherData fetchAndSaveWeather() {
        WeatherResponse response = weatherClient.getWeather(city, apiKey);
        WeatherData data = new WeatherData(null, response.name(), response.main().temp(), LocalDateTime.now());
        return weatherRepository.save(data);
    }

    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public WeatherResponse getWeatherByCity(String city, String lang) {
        GeoLocation geoLocation = weatherClient.getCoordinates(city);
        return getWeather(geoLocation.lat(), geoLocation.lon(), lang);
    }
}
