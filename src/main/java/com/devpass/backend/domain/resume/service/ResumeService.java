package com.devpass.backend.domain.resume.service;

import com.devpass.backend.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.backend.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.backend.global.config.OpenAIConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final DevExperienceAggregateService devExperienceAggregateService;
    private final OpenAIConfig openAIConfig;
    private final ObjectMapper objectMapper;

    public ResumeResponseDTO generateResume(Long devExperienceId) {
        // 1. 개발 경험 집계 데이터(프로젝트, 인턴십, 기술 스택 등)를 조회
        var aggregate = devExperienceAggregateService.getAggregateByDevExperienceId(devExperienceId);

        String prompt = buildPrompt(aggregate);

        // 3. OpenAIConfig를 통해 GPT API 호출 및 응답 확인(로그로 확인 가능)
        String gptResponse = openAIConfig.callGPTApi(prompt);

        // 4. GPT 응답(JSON 문자열)을 ResumeResponseDTO 객체로 파싱 후 반환
        try {
            return objectMapper.readValue(gptResponse, ResumeResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse GPT response", e);
        }
    }

    private String buildPrompt(Object aggregate) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("아래 개발 경험 데이터를 바탕으로 다음과 같은 JSON 포맷의 한국어 이력서를 생성해줘:\n\n");
        prompt.append("{\n");
        prompt.append("  \"summary\": [\"...\"],\n");
        prompt.append("  \"experience\": [ { ... } ],\n");
        prompt.append("  \"activities\": [ { ... } ],\n");
        prompt.append("  \"skills\": [ { ... } ],\n");
        prompt.append("  \"education\": { ... }\n");
        prompt.append("}\n\n");
        prompt.append("개발 경험 데이터 (프로젝트, 기술 스택, 인턴십 등): ").append(aggregate.toString());
        prompt.append("\n\n위 데이터를 참고하여 위 구조에 맞는 한국어 이력서 JSON을 생성해줘.");
        return prompt.toString();
    }
}
