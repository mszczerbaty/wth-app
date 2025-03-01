package com.example.wth_app.service.webflux;

import com.example.wth_app.client.webflux.WebfluxWeatherClient;
import com.example.wth_app.dto.WeatherResponse;
import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.model.WeatherData;
import com.example.wth_app.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class WebfluxWeatherServiceImpl {
    private final WebfluxWeatherClient weatherClient;
    private final WeatherRepository weatherRepository;
    private final TemplateEngine templateEngine;

    public Mono<WeatherResponse> getWeather(double latitude, double longitude, String lang) {
        return weatherClient.getWeather(latitude, longitude, lang);
    }

    public Mono<WeatherResponseDTO> getWeatherByCity(String city, String lang) {
        return weatherClient.getCoordinates(city)
                .flatMap(geoLocation -> getWeather(geoLocation.lat(), geoLocation.lon(), lang))
                .map(WeatherResponseDTO::from);
    }

    public void saveWeatherData(String city) {
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

    public Mono<String> getWeatherReport(String city) {
        return weatherClient.getCoordinates(city)
                .flatMap(geoLocation -> weatherClient.getWeather(geoLocation.lat(), geoLocation.lon(), "en"))
                .map(weatherResponse -> String.format("Weather update for %s:\nTemperature: %.2f°C\nHumidity: %d%%\nCondition: %s",
                        city, weatherResponse.main().temp(), weatherResponse.main().humidity(), weatherResponse.weather().getFirst().description()));
    }

    public Mono<String> getHtmlWeatherByCity(String city, String lang) {
        saveWeatherData(city);
        return getWeatherByCity(city, lang)
                .map(this::createWeatherHtmlResponse);
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
