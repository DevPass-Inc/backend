package com.devpass.backend.global.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OpenAIConfig {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIConfig.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String callGPTApi(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);

        try {
            String requestBodyStr = objectMapper.writeValueAsString(requestBody);
            logger.info("Sending GPT API request: {}", requestBodyStr);
        } catch (Exception e) {
            logger.error("Failed to serialize request body", e);
        }

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String body = response.getBody();
            logger.info("Received GPT API response: {}", body);
            try {
                JsonNode root = objectMapper.readTree(body);
                return root.path("choices").get(0).path("message").path("content").asText();
            } catch (Exception e) {
                logger.error("Failed to parse GPT response", e);
                throw new RuntimeException("Failed to parse GPT response", e);
            }
        } else {
            logger.error("GPT API 호출 실패: {}", response.getStatusCode());
            throw new RuntimeException("GPT API 호출 실패: " + response.getStatusCode());
        }
    }
}
