package com.example.wth_app.service.webflux.impl;

import com.example.wth_app.client.webflux.WebfluxWeatherClient;
import com.example.wth_app.dto.AirQualityResponse;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.repository.WeatherRepository;
import com.example.wth_app.service.impl.AbstractWeatherService;
import com.example.wth_app.service.webflux.WebfluxWeatherService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class WebfluxWeatherServiceImpl extends AbstractWeatherService implements WebfluxWeatherService {
    private final WebfluxWeatherClient weatherClient;

    public WebfluxWeatherServiceImpl(WebfluxWeatherClient weatherClient, WeatherRepository weatherRepository, TemplateEngine templateEngine) {
        super(templateEngine, weatherRepository);
        this.weatherClient = weatherClient;
    }

    public Mono<WeatherResponse> getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public Mono<WeatherResponseDTO> getWeatherByCity(String city, String lang) {
        return weatherClient.getCoordinates(city)
                .flatMap(geoLocation -> {
                    Mono<WeatherResponse> weatherMono = getWeather(geoLocation.lat(), geoLocation.lon(), lang);

                    Mono<AirQualityResponse> airQualityMono = weatherClient.getAirQuality(geoLocation.lat(), geoLocation.lon());

                    return weatherMono.zipWith(airQualityMono)
                            .map(tuple -> {
                                WeatherResponse weatherResponse = tuple.getT1();
                                AirQualityResponse airQualityResponse = tuple.getT2();
                                return WeatherResponseDTO.from(weatherResponse, airQualityResponse);
                            });
                });
    }

    public void getAndSaveWeatherData(String city) {
        getWeatherByCity(city, "en")
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(weatherResponseDTO -> weatherRepository.save(new WeatherData(
                        city,
                        weatherResponseDTO.temperature(),
                        weatherResponseDTO.humidity(),
                        weatherResponseDTO.description()
                )))
                .subscribe();
    }

    public Mono<String> getHtmlWeatherByCity(String city, String lang) {
        return getWeatherByCity(city, lang)
                .map(this::createWeatherHtmlResponse);
    }

}
