// ResumeService.java (일부 수정)
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
    private final ResumePersistenceService resumePersistenceService;

    public ResumeResponseDTO generateAndSaveResume(Long devExperienceId) {
        // 1. 개발 경험 데이터 조회
        var aggregate = devExperienceAggregateService.getAggregateByDevExperienceId(devExperienceId);

        // 2. 프롬프트 생성 (devExperience 데이터를 포함하는 JSON 형식)
        String prompt = buildPrompt(aggregate);

        // 3. GPT API 호출
        String gptResponse = openAIConfig.callGPTApi(prompt);

        // 4. 응답 파싱
        ResumeResponseDTO resumeResponseDTO;
        try {
            resumeResponseDTO = objectMapper.readValue(gptResponse, ResumeResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse GPT response", e);
        }

        // 5. MongoDB에 저장
        resumePersistenceService.saveResume(resumeResponseDTO);

        return resumeResponseDTO;
    }

    private String buildPrompt(Object aggregate) {
        String aggregateJson;
        try {
            aggregateJson = objectMapper.writeValueAsString(aggregate);
        } catch (Exception e) {
            aggregateJson = aggregate.toString();
        }

        StringBuilder prompt = new StringBuilder();
        prompt.append("아래 개발 경험 데이터를 참고하여, 오직 해당 내용만 사용해 다음 JSON 포맷의 이력서를 생성해줘.\n\n");
        prompt.append("JSON 형식은 아래와 같이 작성되어야 해:\n\n");
        prompt.append("{\n");
        prompt.append("  \"summary\": [\n");
        prompt.append("    \"백엔드 개발자로서 다양한 대규모 서비스의 설계 및 개발을 주도한 경험이 있습니다.\",\n");
        prompt.append("    \"다양한 팀과 협업하며 RESTful API 및 마이크로서비스 아키텍처 개발 경험이 풍부합니다.\",\n");
        prompt.append("    \"성능 최적화 및 보안 강화를 위한 시스템 개선 프로젝트에 참여한 경험이 있습니다.\",\n");
        prompt.append("    \"CI/CD 자동화 파이프라인 구축 및 운영 경험이 있으며, Jenkins, GitHub Actions를 사용하여 배포 효율을 높였습니다.\",\n");
        prompt.append("    \"클라우드 환경(AWS, GCP)에서 인프라 구축 및 운영, 비용 최적화와 보안 정책 수립까지 직접 경험했습니다.\"\n");
        prompt.append("  ],\n");
        prompt.append("  \"experience\": [\n");
        prompt.append("    {\n");
        prompt.append("      \"project\": \"\",\n");
        prompt.append("      \"summary\": \"\",\n");
        prompt.append("      \"position\": \"\",\n");
        prompt.append("      \"duration\": \"\",\n");
        prompt.append("      \"skills\": [],\n");
        prompt.append("      \"description\": []\n");
        prompt.append("    }\n");
        prompt.append("  ],\n");
        prompt.append("  \"activities\": [\n");
        prompt.append("    {\n");
        prompt.append("      \"activity\": \"\",\n");
        prompt.append("      \"dates\": \"\"\n");
        prompt.append("    }\n");
        prompt.append("  ],\n");
        prompt.append("  \"skills\": [\n");
        prompt.append("    {\n");
        prompt.append("      \"skill\": \"\",\n");
        prompt.append("      \"level\": \"\"\n");
        prompt.append("    }\n");
        prompt.append("  ]\n");
        prompt.append("}\n\n");
        prompt.append("아래는 개발 경험 데이터(프로젝트, 인턴십, 기술 스택 등)의 상세 JSON입니다:\n");
        prompt.append(aggregateJson);
        prompt.append("\n\n");
        prompt.append("주의사항:\n");
        prompt.append("- 'activities' 필드는 인턴십 데이터를 기반으로 채워줘.\n");
        prompt.append("- 'skills' 필드는 프로젝트 및 인턴십에서 사용한 기술 스택 데이터를 모두 포함하여 그룹화해줘.\n");
        prompt.append("위 데이터를 참고하여, JSON 구조에 맞게 모든 내용을 devExperience 데이터로 채워서 이력서를 생성해줘.");
        return prompt.toString();
    }
}
