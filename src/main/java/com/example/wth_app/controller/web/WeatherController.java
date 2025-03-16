package com.example.wth_app.controller.web;

import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.service.web.WebWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WebWeatherService weatherService;

    @GetMapping(path = "/city")
    public String getWeatherPageByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "en") String lang, Model model) {
        WeatherResponseDTO weather = weatherService.getWeatherByCity(city, lang);
        addWeatherPageModelAttributes(model, weather);
        return "weather-page";
    }

    private static void addWeatherPageModelAttributes(Model model, WeatherResponseDTO weather) {
        model.addAttribute("city", weather.city());
        model.addAttribute("country", weather.country());
        model.addAttribute("temperature", weather.temperature());
        model.addAttribute("feelsLike", weather.feelsLike());
        model.addAttribute("humidity", weather.humidity());
        model.addAttribute("windSpeed", weather.windSpeed());
        model.addAttribute("windDirection", weather.windDirection());
        model.addAttribute("cloudiness", weather.cloudiness());
        model.addAttribute("rainLastHour", weather.rainLastHour());
        model.addAttribute("description", weather.description());
        model.addAttribute("timestamp", weather.timestamp());

        model.addAttribute("airQualityIndex", weather.airQualityIndex());
        model.addAttribute("co", weather.co());
        model.addAttribute("no", weather.no());
        model.addAttribute("no2", weather.no2());
        model.addAttribute("o3", weather.o3());
        model.addAttribute("so2", weather.so2());
        model.addAttribute("pm2_5", weather.pm2_5());
        model.addAttribute("pm10", weather.pm10());
        model.addAttribute("nh3", weather.nh3());
    }

}
