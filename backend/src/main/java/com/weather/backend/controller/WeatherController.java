package com.weather.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.backend.model.ActivitySuggestion;
import com.weather.backend.model.WeatherResponse;
import com.weather.backend.service.GeminiService;
import com.weather.backend.service.WeatherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final GeminiService geminiService;

    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }

    @GetMapping("/{city}/activities")
    public ActivitySuggestion getActivities(@PathVariable String city) {
        WeatherResponse weather = weatherService.getWeather(city);
        return geminiService.suggestActivities(weather);
    }
}