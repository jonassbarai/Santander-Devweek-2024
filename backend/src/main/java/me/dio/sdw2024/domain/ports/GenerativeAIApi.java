package me.dio.sdw2024.domain.ports;

public interface GenerativeAIApi {
    String generateContent(String objective, String context);
}
