package com.example.wth_app.controller.webflux;

import com.example.wth_app.model.dto.WeatherResponse;
import com.example.wth_app.model.dto.WeatherResponseDTO;
import com.example.wth_app.service.webflux.WebfluxWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/weather/v2")
@RequiredArgsConstructor
public class WebfluxWeatherController {
    private final WebfluxWeatherService weatherService;

    @GetMapping(path = "/coords")
    public Mono<WeatherResponse> getWeatherByCoords(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "en") String lang) {
        return weatherService.getWeather(lat, lon, lang);
    }

    @GetMapping(path = "/city")
    public Mono<WeatherResponseDTO> getWeatherByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "en") String lang) {
        return weatherService.getWeatherByCity(city, lang);
    }

}
