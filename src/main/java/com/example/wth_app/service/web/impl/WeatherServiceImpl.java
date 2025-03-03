package com.example.wth_app.service.web.impl;

import com.example.wth_app.client.web.WeatherClient;
import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.repository.WeatherRepository;
import com.example.wth_app.service.web.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;
    private final TemplateEngine templateEngine;

    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public WeatherResponseDTO getWeatherByCity(String city, String lang) {
        GeoLocation geoLocation = weatherClient.getCoordinates(city);
        return WeatherResponseDTO.from(getWeather(geoLocation.lat(), geoLocation.lon(), lang));
    }

    public void saveWeatherData(String city) {
        WeatherResponseDTO weather = getWeatherByCity(city, "en");
        WeatherData weatherData = new WeatherData(
                city,
                weather.temperature(),
                weather.humidity(),
                weather.description()
        );
        weatherRepository.save(weatherData);
    }

    public String getWeatherReport(String city) {
        GeoLocation location = weatherClient.getCoordinates(city);
        WeatherResponse weather = weatherClient.getWeather(location.lat(), location.lon(), "en");

        return String.format("Weather update for %s:\nTemperature: %.2f°C\nHumidity: %d%%\nCondition: %s",
                city, weather.main().temp(), weather.main().humidity(), weather.weather().getFirst().description());
    }

    public String getHtmlWeatherByCity(String city, String lang) {
        WeatherResponseDTO weatherResponseDTO = getWeatherByCity(city, lang);
        return createWeatherHtmlResponse(weatherResponseDTO);
    }

    private String createWeatherHtmlResponse(WeatherResponseDTO weather) {
        Context context = new Context();
        context.setVariable("city", weather.city());
        context.setVariable("country", weather.country());
        context.setVariable("temperature", weather.temperature());
        context.setVariable("feelsLike", weather.feelsLike());
        context.setVariable("humidity", weather.humidity());
        context.setVariable("windSpeed", weather.windSpeed());
        context.setVariable("windDirection", weather.windDirection());
        context.setVariable("cloudiness", weather.cloudiness());
        context.setVariable("rainLastHour", weather.rainLastHour());
        context.setVariable("description", weather.description());
        context.setVariable("timestamp", weather.timestamp());
        return templateEngine.process("weather-email", context);
    }


}
