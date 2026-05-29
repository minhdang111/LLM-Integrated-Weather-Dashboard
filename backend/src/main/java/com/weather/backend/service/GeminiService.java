package com.weather.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.backend.model.ActivitySuggestion;
import com.weather.backend.model.WeatherResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final WebClient.Builder webClientBuilder;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public ActivitySuggestion suggestActivities(WeatherResponse weather) {
        String prompt = String.format(
            "The weather in %s is %s. Temperature is %.1f°F, feels like %.1f°F, " +
            "humidity is %d%%, wind speed is %.1f mph. " +
            "Suggest 5 fun and practical activities suitable for this weather. " +
            "Format as a short numbered list.",
            weather.getCity(), weather.getDescription(),
            weather.getTemperature(), weather.getFeelsLike(),
            weather.getHumidity(), weather.getWindSpeed()
        );

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(Map.of("text", prompt)))
            )
        );

        JsonNode response = webClientBuilder.build()
                .post()
                .uri(apiUrl + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        String text = response
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        ActivitySuggestion suggestion = new ActivitySuggestion();
        suggestion.setSuggestion(text);
        return suggestion;
    }
}
