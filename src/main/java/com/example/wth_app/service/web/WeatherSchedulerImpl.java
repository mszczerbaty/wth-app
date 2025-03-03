package com.example.wth_app.service.web;


import com.example.wth_app.model.WeatherSubscription;
import com.example.wth_app.service.WeatherSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherSchedulerImpl {
    private final WeatherService weatherService;
    private final WeatherSubscriptionService subscriptionService;

    @Scheduled(fixedRate = 3600000)
    public void fetchAndStoreWeather() {
        log.info("Fetching weather data {}", LocalDateTime.now());
        weatherService.saveWeatherData("Warsaw");
    }

    @Scheduled(cron = "0 0 * * * *")
    public void sendWeatherEmails() {
        List<WeatherSubscription> subscriptions = subscriptionService.getAllSubscriptions();

        for (WeatherSubscription sub : subscriptions) {
            String weatherReport = weatherService.getWeatherReport(sub.getCity());
            String subject = "Weather Update for " + sub.getCity();
            log.info("{}\n{}", weatherReport, subject);
        }
    }

}
