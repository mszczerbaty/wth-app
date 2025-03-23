package com.example.wth_app.client.webflux;

import com.example.wth_app.model.dto.AirQualityResponse;
import com.example.wth_app.model.dto.GeoLocation;
import com.example.wth_app.model.dto.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WebfluxWeatherClient {
    Mono<WeatherResponse> getWeather(String city, String apiKey);

    Mono<WeatherResponse> getWeather(double latitude, double longitude, String lang);

    Mono<GeoLocation> getCoordinates(String city);

    Mono<AirQualityResponse> getAirQuality(double latitude, double longitude);
}
