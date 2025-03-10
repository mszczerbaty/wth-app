package com.example.wth_app.service.impl;

import com.example.wth_app.dto.WeatherResponseDTO;
import com.example.wth_app.repository.WeatherRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class AbstractWeatherService {
    protected final TemplateEngine templateEngine;
    protected final WeatherRepository weatherRepository;

    protected AbstractWeatherService(TemplateEngine templateEngine, WeatherRepository weatherRepository) {
        this.templateEngine = templateEngine;
        this.weatherRepository = weatherRepository;
    }

    public abstract void getAndSaveWeatherData(String city);

    protected String createWeatherHtmlResponse(WeatherResponseDTO weather) {
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

        context.setVariable("airQualityIndex", weather.airQualityIndex());
        context.setVariable("co", weather.co());
        context.setVariable("no", weather.no());
        context.setVariable("no2", weather.no2());
        context.setVariable("o3", weather.o3());
        context.setVariable("so2", weather.so2());
        context.setVariable("pm2_5", weather.pm2_5());
        context.setVariable("pm10", weather.pm10());
        context.setVariable("nh3", weather.nh3());

        return templateEngine.process("weather-email", context);
    }
}
