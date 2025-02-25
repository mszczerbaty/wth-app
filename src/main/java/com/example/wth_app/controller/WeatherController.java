package com.example.wth_app.controller;

import com.example.wth_app.model.WeatherData;
import com.example.wth_app.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping(path = "/current")
    public WeatherData getCurrentWeather() {
        return weatherService.fetchAndSaveWeather();
    }

}
