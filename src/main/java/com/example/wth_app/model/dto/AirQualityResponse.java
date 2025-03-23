package com.example.wth_app.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AirQualityResponse(
        @JsonProperty("coord") Coord coord,
        @JsonProperty("list") List<AirQualityData> list
) {
    public record Coord(
            @JsonProperty("lon") double lon,
            @JsonProperty("lat") double lat
    ) {
    }

    public record AirQualityData(
            @JsonProperty("dt") long dt,
            @JsonProperty("main") MainData main,
            @JsonProperty("components") Components components
    ) {
    }

    public record MainData(
            @JsonProperty("aqi") int aqi
    ) {
    }

    public record Components(
            @JsonProperty("co") double co,
            @JsonProperty("no") double no,
            @JsonProperty("no2") double no2,
            @JsonProperty("o3") double o3,
            @JsonProperty("so2") double so2,
            @JsonProperty("pm2_5") double pm2_5,
            @JsonProperty("pm10") double pm10,
            @JsonProperty("nh3") double nh3
    ) {
    }
}

