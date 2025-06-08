package com.example.wth_app.controller.rest;

import com.example.wth_app.service.WeatherSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email/v1")
@RequiredArgsConstructor
public class RestWeatherSubscriptionController {
    private final WeatherSubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String email, @RequestParam String city) {
        return ResponseEntity.ok(subscriptionService.subscribe(email, city));
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam String email, @RequestParam String city) {
        return ResponseEntity.ok(subscriptionService.unsubscribe(email, city));
    }
}
