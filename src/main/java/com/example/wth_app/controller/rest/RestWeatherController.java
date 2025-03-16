package com.example.wth_app.controller.rest;

import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.service.web.WebWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/weather/v1")
@RequiredArgsConstructor
public class RestWeatherController {
    private final WebWeatherService weatherService;

    @GetMapping(path = "/coords")
    public ResponseEntity<WeatherResponse> getWeatherByCoords(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "en") String lang) {
        WeatherResponse weatherResponse = weatherService.getWeather(lat, lon, lang);
        return ResponseEntity.ok(weatherResponse);
    }

    @GetMapping(path = "/city")
    public ResponseEntity<WeatherResponseDTO> getWeatherByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "en") String lang) {
        WeatherResponseDTO weatherResponseDTO = weatherService.getWeatherByCity(city, lang);
        return ResponseEntity.ok(weatherResponseDTO);
    }

}
