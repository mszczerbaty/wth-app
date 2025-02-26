package com.example.wth_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        String name,
        @JsonProperty("main") Main main
) {
    public record Main(double temp) {
    }
}
