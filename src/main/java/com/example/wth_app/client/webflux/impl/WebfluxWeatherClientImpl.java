package com.example.wth_app.client.webflux.impl;

import com.example.wth_app.client.webflux.WebfluxWeatherClient;
import com.example.wth_app.dto.AirQualityResponse;
import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.error.CityNotFoundException;
import com.example.wth_app.error.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebfluxWeatherClientImpl implements WebfluxWeatherClient {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${geo.api.url}")
    private String geoApiUrl;
    @Value("${weather.api.url}")
    private String weatherApiUrl;
    private final WebClient webClient;


    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public Mono<WeatherResponse> getWeather(String city, String apiKey) {
        log.info("Retrieving weather data for city: {}", city);
        return webClient.get()
                .uri("https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric", city, apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new CityNotFoundException("City not found: " + city)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalServiceException("Weather service unavailable")))
                .bodyToMono(WeatherResponse.class);
    }

    @Cacheable(value = "weatherByCoords", key = "#longitude.toString() + #latitude.toString()", unless = "#result == null")
    public Mono<WeatherResponse> getWeather(double latitude, double longitude, String lang) {
        String url = UriComponentsBuilder.fromUriString(weatherApiUrl)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", lang)
                .toUriString();

        log.info("Retrieving weather data for coordinates: {}, {}", latitude, longitude);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                    log.error("Weather data not found for coordinates: {}, {}", latitude, longitude);
                    return Mono.empty();
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching weather data: {}", e.getMessage());
                    return Mono.error(new ExternalServiceException("Weather service unavailable"));
                });
    }

    @Cacheable(value = "weatherByCity", key = "#city", unless = "#result == null")
    public Mono<GeoLocation> getCoordinates(String city) {
        String url = UriComponentsBuilder.fromUriString(geoApiUrl)
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", apiKey)
                .toUriString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(GeoLocation[].class)
                .flatMap(response -> {
                    if (response != null && response.length > 0) {
                        return Mono.just(response[0]);
                    } else {
                        return Mono.error(new CityNotFoundException("City not found: " + city));
                    }
                });
    }

    @Cacheable(value = "weatherByCoords", key = "#longitude.toString() + #latitude.toString()", unless = "#result == null")
    public Mono<AirQualityResponse> getAirQuality(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromUriString(weatherApiUrl)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .toUriString();

        log.info("Retrieving ait quality data for coordinates: {}, {}", latitude, longitude);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(AirQualityResponse.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                    log.error("Weather data not found for coordinates: {}, {}", latitude, longitude);
                    return Mono.empty();
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching weather data: {}", e.getMessage());
                    return Mono.error(new ExternalServiceException("Weather service unavailable"));
                });
    }
}
