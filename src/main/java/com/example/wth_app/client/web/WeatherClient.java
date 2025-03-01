package com.example.wth_app.client.web;

import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.error.CityNotFoundException;
import com.example.wth_app.error.ExternalServiceException;
import com.example.wth_app.error.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherClient {
    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${geo.api.url}")
    private String geoApiUrl;
    @Value("${weather.api.url}")
    private String weatherApiUrl;
    private final RestTemplate restTemplate;


    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public WeatherResponse getWeather(String city, String apiKey) {
        log.info("Retrieving weather data for city: {}", city);
        try {
            String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";
            return restTemplate.getForObject(OPEN_WEATHER_URL, WeatherResponse.class, city, apiKey);
        } catch (HttpClientErrorException.NotFound e) {
            throw new CityNotFoundException("City not found: " + city);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new UnauthorizedException("Unauthorized..." + e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ExternalServiceException("Weather service unavailable: " + e.getMessage());
        } catch (Exception e) {
            throw new ExternalServiceException("Unexpected error: " + e.getMessage());
        }
    }

    @Cacheable(value = "weatherByCoords", key = "#longitude.toString() + #latitude.toString()", unless = "#result == null")
    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        String url = UriComponentsBuilder.fromUriString(weatherApiUrl)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", lang)
                .toUriString();
        log.info("Retrieving weather data for city: {}", url);

        return restTemplate.getForObject(url, WeatherResponse.class);
    }

    @Cacheable(value = "weatherByCity", key = "#city", unless = "#result == null")
    public GeoLocation getCoordinates(String city) {
        String url = UriComponentsBuilder.fromUriString(geoApiUrl)
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", apiKey)
                .toUriString();

        GeoLocation[] response = restTemplate.getForObject(url, GeoLocation[].class);
        if (response != null && response.length > 0) {
            return response[0];
        } else {
            throw new CityNotFoundException("City not found: " + city);
        }
    }
}
