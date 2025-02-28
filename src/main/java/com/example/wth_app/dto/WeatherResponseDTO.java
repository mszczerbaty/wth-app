package com.example.wth_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponseDTO(
        String city,
        String country,
        double temperature,
        double feelsLike,
        double tempMin,
        double tempMax,
        int humidity,
        int pressure,
        String description,
        double windSpeed,
        int windDirection,
        Double windGust,
        int cloudiness,
        Double rainLastHour,
        LocalDateTime timestamp
) {
    public static WeatherResponseDTO from(WeatherResponse response) {
        return new WeatherResponseDTO(
                response.name(),
                response.sys().country(),
                response.main().temp(),
                response.main().feelsLike(),
                response.main().tempMin(),
                response.main().tempMax(),
                response.main().humidity(),
                response.main().pressure(),
                response.weather().getFirst().description(),
                response.wind().speed(),
                response.wind().deg(),
                response.wind().gust(),
                response.clouds().all(),
                response.rain() != null ? response.rain().oneHour() : null,
                LocalDateTime.now()
        );
    }
}
