package com.example.wth_app.service.web.impl;

import com.example.wth_app.client.web.WebWeatherClient;
import com.example.wth_app.model.User;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.model.WeatherRequest;
import com.example.wth_app.model.dto.AirQualityResponse;
import com.example.wth_app.model.dto.GeoLocation;
import com.example.wth_app.model.dto.WeatherResponse;
import com.example.wth_app.model.dto.WeatherResponseDTO;
import com.example.wth_app.model.mapper.WeatherRequestMapper;
import com.example.wth_app.repository.UserRepository;
import com.example.wth_app.repository.WeatherRepository;
import com.example.wth_app.repository.WeatherRequestRepository;
import com.example.wth_app.service.impl.AbstractWeatherService;
import com.example.wth_app.service.web.WebWeatherService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


@Service
public class WebWeatherServiceImpl extends AbstractWeatherService implements WebWeatherService {
    private final WebWeatherClient weatherClient;
    private final WeatherRequestRepository weatherRequestRepository;
    private final UserRepository userRepository;

    public WebWeatherServiceImpl(WebWeatherClient weatherClient, WeatherRepository weatherRepository, TemplateEngine templateEngine, WeatherRequestRepository weatherRequestRepository, UserRepository userRepository) {
        super(templateEngine, weatherRepository);
        this.weatherClient = weatherClient;
        this.weatherRequestRepository = weatherRequestRepository;
        this.userRepository = userRepository;
    }

    public WeatherResponse getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public WeatherResponseDTO getWeatherByCity(String city, String lang) {
        GeoLocation geoLocation = weatherClient.getCoordinates(city);
        AirQualityResponse airQuality = weatherClient.getAirQuality(geoLocation.lat(), geoLocation.lon());
        WeatherResponse weatherResponse = getWeather(geoLocation.lat(), geoLocation.lon(), lang);
        WeatherResponseDTO weatherResponseDTO = WeatherResponseDTO.from(weatherResponse, airQuality);
        User user = getCurrentUser();
        WeatherRequest weatherRequest = WeatherRequestMapper.INSTANCE.weatherResponseDTOWithUserToWeatherRequest(weatherResponseDTO, user);
        weatherRequestRepository.save(weatherRequest);
        return weatherResponseDTO;
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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
