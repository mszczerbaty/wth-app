package com.example.wth_app.model.mapper;

import com.example.wth_app.model.User;
import com.example.wth_app.model.WeatherRequest;
import com.example.wth_app.model.dto.WeatherResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class WeatherRequestMapper {
    public static WeatherRequest mapDtoToEntity(WeatherResponseDTO dto, User user) {
        return WeatherRequest.builder()
                .city(dto.city())
                .country(dto.country())
                .temperature(dto.temperature())
                .feelsLike(dto.feelsLike())
                .tempMin(dto.tempMin())
                .tempMax(dto.tempMax())
                .humidity(dto.humidity())
                .pressure(dto.pressure())
                .description(dto.description())
                .windSpeed(dto.windSpeed())
                .windDirection(dto.windDirection())
                .windGust(dto.windGust())
                .cloudiness(dto.cloudiness())
                .rainLastHour(dto.rainLastHour())
                .timestamp(dto.timestamp())
                .airQualityIndex(dto.airQualityIndex())
                .co(dto.co())
                .no(dto.no())
                .no2(dto.no2())
                .o3(dto.o3())
                .so2(dto.so2())
                .pm2_5(dto.pm2_5())
                .pm10(dto.pm10())
                .nh3(dto.nh3())
                .user(user)
                .build();
    }
}
