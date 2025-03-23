package com.example.wth_app.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeoLocation(
        @JsonProperty("lat") double lat,
        @JsonProperty("lon") double lon
) {
}
