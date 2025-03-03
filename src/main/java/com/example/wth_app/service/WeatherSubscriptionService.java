package com.example.wth_app.service;

import com.example.wth_app.model.WeatherSubscription;

import java.util.List;

public interface WeatherSubscriptionService {
    String subscribe(String email, String city);

    String unsubscribe(String email, String city);

    List<WeatherSubscription> getAllSubscriptions();
}
