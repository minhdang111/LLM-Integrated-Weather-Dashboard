package com.weather.backend.model;

import lombok.Data;

@Data
public class WeatherResponse {
    private String city;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private double windSpeed;
    private String description;
    private String icon;
}