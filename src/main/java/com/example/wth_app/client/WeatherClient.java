package com.example.wth_app.client;

import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.error.CityNotFoundException;
import com.example.wth_app.error.ExternalServiceException;
import com.example.wth_app.error.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WeatherClient {
    private final RestTemplate restTemplate;

    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public WeatherResponse getWeather(String city, String apiKey) {
        String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";
        try {
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
}
