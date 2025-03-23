package com.example.wth_app.service.webflux;

import com.example.wth_app.model.dto.WeatherResponse;
import com.example.wth_app.model.dto.WeatherResponseDTO;
import reactor.core.publisher.Mono;

public interface WebfluxWeatherService {
    Mono<WeatherResponse> getWeather(double latitude, double longitude, String lang);

    Mono<WeatherResponseDTO> getWeatherByCity(String city, String lang);

    void getAndSaveWeatherDataByCity(String city);

    Mono<String> getHtmlWeatherByCity(String city, String lang);
}
