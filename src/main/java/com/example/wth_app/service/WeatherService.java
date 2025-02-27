package com.example.wth_app.service;

import com.example.wth_app.client.WeatherClient;
import com.example.wth_app.dto.GeoLocation;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;

    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public WeatherResponse getWeatherByCity(String city, String lang) {
        GeoLocation geoLocation = weatherClient.getCoordinates(city);
        return getWeather(geoLocation.lat(), geoLocation.lon(), lang);
    }

    public void saveWeatherData(String city) {
        WeatherResponse weather = getWeatherByCity(city, "en");
        WeatherData weatherData = new WeatherData(
                city,
                weather.main().temp(),
                weather.main().humidity(),
                weather.weather().getFirst().description()
        );
        weatherRepository.save(weatherData);
    }

    public String getWeatherReport(String city) {
        GeoLocation location = weatherClient.getCoordinates(city);
        WeatherResponse weather = weatherClient.getWeather(location.lat(), location.lon(), "en");

        return String.format("Weather update for %s:\nTemperature: %.2f°C\nHumidity: %d%%\nCondition: %s",
                city, weather.main().temp(), weather.main().humidity(), weather.weather().getFirst().description());
    }
}
