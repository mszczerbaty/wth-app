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
        String windDirection,
        Double windGust,
        int cloudiness,
        Double rainLastHour,
        LocalDateTime timestamp,
        Integer airQualityIndex,
        Double co,
        Double no,
        Double no2,
        Double o3,
        Double so2,
        Double pm2_5,
        Double pm10,
        Double nh3
) {

    public static WeatherResponseDTO from(WeatherResponse response, AirQualityResponse airQualityResponse) {
        AirQualityResponse.AirQualityData airData = airQualityResponse.list().getFirst();

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
                getWindDirection(response.wind().deg()),
                response.wind().gust(),
                response.clouds().all(),
                response.rain() != null ? response.rain().oneHour() : null,
                LocalDateTime.now(),
                airData.main().aqi(),
                airData.components().co(),
                airData.components().no(),
                airData.components().no2(),
                airData.components().o3(),
                airData.components().so2(),
                airData.components().pm2_5(),
                airData.components().pm10(),
                airData.components().nh3()
        );
    }

    public static String getWindDirection(int degrees) {
        String[] directions = {"N", "NE", "E", "SE",
                "S", "SW", "W", "NW", "N"};
        return directions[(int) Math.round(((double) degrees % 360) / 45)];
    }
}
