package com.example.wth_app.repository;

import com.example.wth_app.model.WeatherRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRequestRepository extends JpaRepository<WeatherRequest, Long> {
}
