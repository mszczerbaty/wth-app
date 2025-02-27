package com.example.wth_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        @JsonProperty("coord") Coord coord,
        @JsonProperty("weather") List<Weather> weather,
        @JsonProperty("main") Main main,
        @JsonProperty("wind") Wind wind,
        @JsonProperty("rain") Rain rain,
        @JsonProperty("clouds") Clouds clouds,
        @JsonProperty("sys") Sys sys,
        @JsonProperty("timezone") int timezone,
        @JsonProperty("name") String name
) {
    public record Coord(
            @JsonProperty("lon") double lon,
            @JsonProperty("lat") double lat
    ) {
    }

    public record Weather(
            @JsonProperty("id") int id,
            @JsonProperty("main") String main,
            @JsonProperty("description") String description,
            @JsonProperty("icon") String icon
    ) {
    }

    public record Main(
            @JsonProperty("temp") double temp,
            @JsonProperty("feels_like") double feelsLike,
            @JsonProperty("temp_min") double tempMin,
            @JsonProperty("temp_max") double tempMax,
            @JsonProperty("pressure") int pressure,
            @JsonProperty("humidity") int humidity,
            @JsonProperty("sea_level") Integer seaLevel, // Może być null
            @JsonProperty("grnd_level") Integer groundLevel // Może być null
    ) {
    }

    public record Wind(
            @JsonProperty("speed") double speed,
            @JsonProperty("deg") int deg,
            @JsonProperty("gust") Double gust // Może być null
    ) {
    }

    public record Rain(
            @JsonProperty("1h") Double oneHour // Może być null, więc Double
    ) {
    }

    public record Clouds(
            @JsonProperty("all") int all
    ) {
    }

    public record Sys(
            @JsonProperty("country") String country,
            @JsonProperty("sunrise") long sunrise,
            @JsonProperty("sunset") long sunset
    ) {
    }
}

