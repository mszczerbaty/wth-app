package com.example.wth_app.controller.web;

import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.service.web.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherServiceImpl weatherService;

    @GetMapping(path = "/current")
    public ResponseEntity<WeatherResponse> getCurrentWeather(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "en") String lang) {
        WeatherResponse weatherResponse = weatherService.getWeather(lat, lon, lang);
        return ResponseEntity.ok(weatherResponse);
    }

    @GetMapping(path = "/by-city")
    public ResponseEntity<WeatherResponseDTO> getWeatherByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "en") String lang) {
        WeatherResponseDTO weatherResponseDTO = weatherService.getWeatherByCity(city, lang);
        return ResponseEntity.ok(weatherResponseDTO);
    }

    @GetMapping(path = "/status-by-city")
    public ResponseEntity<String> getWeatherStatusByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "en") String lang) {
        String weatherHtml = weatherService.getHtmlWeatherByCity(city, lang);
        return ResponseEntity.ok(weatherHtml);
    }

}
