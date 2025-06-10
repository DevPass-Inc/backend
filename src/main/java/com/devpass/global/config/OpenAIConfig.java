package com.devpass.global.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OpenAIConfig {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIConfig.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.api.key}")
    private String openaiApiKey;

    /**
     * Function Calling 방식으로 GPT에 요청하여, 함수 인자(JSON)만 돌려받습니다.
     */
    public String callGenerateResumeFunction(String promptJson) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        // 1) 함수 스펙 정의 (JSON Schema 형식)
        Map<String, Object> paramsSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "summary", Map.of(
                                "type", "array",
                                "items", Map.of("type", "string")
                        ),
                        "experience", Map.of(
                                "type", "array",
                                "items", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "project", Map.of("type", "string"),
                                                "summary", Map.of("type", "string"),
                                                "position", Map.of("type", "string"),
                                                "duration", Map.of("type", "string"),
                                                "skills", Map.of("type", "string"),
                                                "description", Map.of(
                                                        "type", "array",
                                                        "items", Map.of("type", "string")
                                                )
                                        ),
                                        "required",
                                        List.of("project", "summary", "position", "duration", "skills", "description")
                                )
                        ),
                        "activities", Map.of(
                                "type", "array",
                                "items", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "activity", Map.of("type", "string"),
                                                "dates", Map.of("type", "string")
                                        ),
                                        "required", List.of("activity", "dates")
                                )
                        ),
                        "skills", Map.of(
                                "type", "array",
                                "items", Map.of(
                                        "type", "object",
                                        "properties", Map.of(
                                                "skill", Map.of("type", "string"),
                                                "level", Map.of("type", "string")
                                        ),
                                        "required", List.of("skill", "level")
                                )
                        )
                ),
                "required", List.of("summary", "experience", "activities", "skills")
        );

        Map<String, Object> functionDef = Map.of(
                "name", "generate_resume_response",
                "description", "Returns the resume JSON according to ResumeResponseDTO schema",
                "parameters", paramsSchema
        );

        // 2) 메시지 구성
        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content",
                        "You are a helpful assistant. " +
                                "When returning the resume, call the function `generate_resume_response` " +
                                "with exactly the JSON matching our schema, without markdown or extra text." +
                                "응답은 **모두 한국어로만** 작성해주세요."
                ),
                Map.of("role", "user", "content", promptJson)
        );

        // 3) 요청 바디 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o");
        requestBody.put("messages", messages);
        requestBody.put("functions", List.of(functionDef));
        requestBody.put("function_call", Map.of("name", "generate_resume_response"));

        // 로깅
        try {
            logger.info("Sending GPT FunctionCall request: {}", objectMapper.writeValueAsString(requestBody));
        } catch (Exception ignored) {
        }

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> resp = restTemplate.postForEntity(url, entity, String.class);

        if (resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null) {
            logger.error("GPT API 호출 실패: {}", resp.getStatusCode());
            throw new RuntimeException("GPT API 호출 실패: ");
        }

        String body = resp.getBody();
        logger.info("Received GPT FunctionCall response: {}", body);

        try {
            JsonNode root = objectMapper.readTree(body);
            JsonNode funcCall = root.path("choices").get(0)
                    .path("message")
                    .path("function_call");
            String args = funcCall.path("arguments").asText();
            return args;
        } catch (Exception e) {
            logger.error("Failed to parse function_call arguments", e);
            throw new RuntimeException("Failed to parse GPT function response");
        }
    }
}