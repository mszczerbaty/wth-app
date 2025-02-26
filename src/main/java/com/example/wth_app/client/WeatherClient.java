package com.example.wth_app.client;

import com.example.wth_app.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WeatherClient {
    private final RestTemplate restTemplate;

    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public WeatherResponse getWeather(String city, String apiKey) {
        String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";
        return restTemplate.getForObject(OPEN_WEATHER_URL, WeatherResponse.class, city, apiKey);
    }
}
