package com.example.wth_app.service;

import com.example.wth_app.model.WeatherSubscription;
import com.example.wth_app.repository.WeatherSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherSubscriptionService {
    private final WeatherSubscriptionRepository subscriptionRepository;

    public String subscribe(String email, String city) {
        if (subscriptionRepository.existsByEmailAndCity(email, city)) {
            return "Already subscribed!";
        }

        WeatherSubscription subscription = new WeatherSubscription();
        subscription.setEmail(email);
        subscription.setCity(city);
        subscriptionRepository.save(subscription);

        return "Subscription successful!";
    }

    @Transactional
    public String unsubscribe(String email, String city) {
        if (!subscriptionRepository.existsByEmailAndCity(email, city)) {
            return "Subscription not found!";
        }

        subscriptionRepository.deleteByEmailAndCity(email, city);
        return "Unsubscribed successfully!";
    }

    public List<WeatherSubscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
}
