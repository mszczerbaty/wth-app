package com.example.wth_app.repository;

import com.example.wth_app.model.WeatherSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSubscriptionRepository extends JpaRepository<WeatherSubscription, Long> {
    boolean existsByEmailAndCity(String email, String city);

    void deleteByEmailAndCity(String email, String city);
}
