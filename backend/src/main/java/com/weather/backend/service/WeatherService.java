package com.weather.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.backend.model.WeatherResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private static final double METERS_PER_MILE = 1609.344;
    private static final double SECONDS_PER_HOUR = 3600.0;
    private final WebClient.Builder webClientBuilder;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public WeatherResponse getWeather(String city) {
        JsonNode response = webClientBuilder.build()
                .get()
                .uri(apiUrl + "/weather?q={city}&appid={key}&units=metric", city, apiKey)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        WeatherResponse weather = new WeatherResponse();
        weather.setCity(response.path("name").asText());
        weather.setTemperature(toFarenheith(response.path("main").path("temp").asDouble()));
        weather.setFeelsLike(toFarenheith(response.path("main").path("feels_like").asDouble()));
        weather.setHumidity(response.path("main").path("humidity").asInt());
        weather.setWindSpeed(toMPH(response.path("wind").path("speed").asDouble()));
        weather.setDescription(response.path("weather").get(0).path("description").asText());
        weather.setIcon(response.path("weather").get(0).path("icon").asText());

        return weather;
    }

    private double toFarenheith(double celsius) {
        double fahrenheit = (celsius * 9 / 5) + 32;
        return Math.round(fahrenheit * 10.0) / 10.0;
    }

    private double toMPH(double metersPerSecond) {
        double result = metersPerSecond * SECONDS_PER_HOUR / METERS_PER_MILE;
        return Math.round(result * 10.0) / 10.0;
    }
}