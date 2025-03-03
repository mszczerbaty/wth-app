package com.example.wth_app.service;

public interface EmailService {
    void sendWeatherEmail(String to, String subject, String body);
}
