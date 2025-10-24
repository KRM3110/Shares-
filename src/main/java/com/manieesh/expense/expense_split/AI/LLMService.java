package com.manieesh.expense.expense_split.AI;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LLMService {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public String chatWithLLM(String prompt) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String json = String.format("{\"model\":\"llama3\",\"prompt\":\"%s\"}", prompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            return "AI unavailable: " + e.getMessage();
        }
    }
}

