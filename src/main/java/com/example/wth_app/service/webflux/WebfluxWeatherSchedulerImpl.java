package com.example.wth_app.service.webflux;


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
public class WebfluxWeatherSchedulerImpl {
    private final WebfluxWeatherService weatherService;
    private final WeatherSubscriptionService subscriptionService;
    private final EmailService emailService;

    @Scheduled(fixedRate = 3600000)
    public void fetchAndStoreWeather() {
        log.info("Fetching weather data {}", LocalDateTime.now());
        weatherService.getAndSaveWeatherData("Warsaw");
    }

    @Scheduled(cron = "0 30 * * * *")
    public void sendWeatherEmails() {
        List<WeatherSubscription> subscriptions = subscriptionService.getAllSubscriptions();

        subscriptions.forEach(sub -> {
            String subject = "Weather Update for " + sub.getCity();

            weatherService.getHtmlWeatherByCity(sub.getCity(), "en")
                    .doOnNext(report -> {
                        log.info("{}\n{}", report, subject);
                        emailService.sendWeatherEmail(sub.getEmail(), subject, report);
                    })
                    .subscribe();
        });
    }

}
