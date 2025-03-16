package com.example.wth_app.service.web.impl;

import com.example.wth_app.client.web.WebWeatherClient;
import com.example.wth_app.dto.AirQualityResponse;
import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.repository.WeatherRepository;
import com.example.wth_app.service.impl.AbstractWeatherService;
import com.example.wth_app.service.web.WebWeatherService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class WebWeatherServiceImpl extends AbstractWeatherService implements WebWeatherService {
    private final WebWeatherClient weatherClient;

    public WebWeatherServiceImpl(WebWeatherClient weatherClient, WeatherRepository weatherRepository, TemplateEngine templateEngine) {
        super(templateEngine, weatherRepository);
        this.weatherClient = weatherClient;
    }

    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public WeatherResponseDTO getWeatherByCity(String city, String lang) {
        GeoLocation geoLocation = weatherClient.getCoordinates(city);
        AirQualityResponse airQuality = weatherClient.getAirQuality(geoLocation.lat(), geoLocation.lon());
        WeatherResponse weatherResponse = getWeather(geoLocation.lat(), geoLocation.lon(), lang);
        return WeatherResponseDTO.from(weatherResponse, airQuality);
    }

    public void getAndSaveWeatherDataByCity(String city) {
        WeatherResponseDTO weather = getWeatherByCity(city, "en");
        WeatherData weatherData = new WeatherData(
                city,
                weather.temperature(),
                weather.humidity(),
                weather.description()
        );
        weatherRepository.save(weatherData);
    }

    public String getWeatherHtmlPageByCity(String city, String lang) {
        WeatherResponseDTO weatherResponseDTO = getWeatherByCity(city, lang);
        return createWeatherPageHtmlResponse(weatherResponseDTO);
    }

}
