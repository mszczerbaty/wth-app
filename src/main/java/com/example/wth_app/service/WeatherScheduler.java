package com.example.wth_app.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherScheduler {
    private final WeatherService weatherService;

    @Scheduled(fixedRate = 3600000)
    public void fetchAndStoreWeather() {
        log.info("Fetching weather data {}", LocalDateTime.now());
        weatherService.saveWeatherData("Warsaw");
    }
}
