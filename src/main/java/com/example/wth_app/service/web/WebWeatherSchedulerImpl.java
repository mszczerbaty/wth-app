package com.example.wth_app.service.web;


import com.example.wth_app.model.WeatherSubscription;
import com.example.wth_app.service.EmailService;
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
public class WebWeatherSchedulerImpl {
    private final WebWeatherService weatherService;
    private final WeatherSubscriptionService subscriptionService;
    private final EmailService emailService;

    @Scheduled(fixedRate = 3600000)
    public void fetchAndStoreWeatherForAllSubscribedCities() {
        log.info("Fetching weather data {}", LocalDateTime.now());
        List<WeatherSubscription> subscriptions = subscriptionService.getAllSubscriptions();
        subscriptions.forEach(subscription -> weatherService.getAndSaveWeatherDataByCity(subscription.getCity()));
    }

    @Scheduled(cron = "0 0 8-18 * * *")
    public void sendWeatherEmails() {
        List<WeatherSubscription> subscriptions = subscriptionService.getAllSubscriptions();

        for (WeatherSubscription sub : subscriptions) {
            String weatherReport = weatherService.getWeatherHtmlPageByCity(sub.getCity(), "en");
            String subject = "Weather Update for " + sub.getCity();
            log.info("{}\n{}", weatherReport, subject);
            emailService.sendWeatherEmail(sub.getEmail(), subject, weatherReport);
        }
    }

}
