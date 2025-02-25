package com.example.wth_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private String name;

    @JsonProperty("main")
    private Main main;

    @Getter
    @Setter
    public static class Main {
        private double temp;
    }
}
