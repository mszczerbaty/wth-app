package com.example.wth_app.client;

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
    private final RestTemplate restTemplate;
    private static final String GEO_API_URL = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";


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

    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        String url = UriComponentsBuilder.fromUriString(WEATHER_API_URL)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", lang)
                .toUriString();
        log.info("Retrieving weather data for city: {}", url);

        return restTemplate.getForObject(url, WeatherResponse.class);
    }

    public GeoLocation getCoordinates(String city) {
        String url = UriComponentsBuilder.fromUriString(GEO_API_URL)
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
