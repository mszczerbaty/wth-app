package com.example.wth_app.model.mapper;

import com.example.wth_app.model.User;
import com.example.wth_app.model.WeatherRequest;
import com.example.wth_app.model.dto.WeatherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherRequestMapper {
    WeatherRequestMapper INSTANCE = Mappers.getMapper(WeatherRequestMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(ignore = true, target = "id")
    WeatherRequest weatherResponseDTOWithUserToWeatherRequest(WeatherResponseDTO weatherResponseDTO, User user);
}
