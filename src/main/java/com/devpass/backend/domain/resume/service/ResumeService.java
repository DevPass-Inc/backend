package com.devpass.backend.domain.resume.service;

import com.devpass.backend.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.backend.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.backend.domain.resume.dto.ResumePromptDTO;
import com.devpass.backend.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.backend.domain.resume.util.ResumePrompt;
import com.devpass.backend.global.config.OpenAIConfig;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final DevExperienceAggregateService devExperienceAggregateService;
    private final OpenAIConfig openAIConfig;
    private final ObjectMapper objectMapper;

    public ResumeResponseDTO generateResume(Long devExperienceId) {
        // 1. 개발 경험 데이터(프로젝트, 인턴십, 기술 스택 등)를 조회
        DevExperienceAggregateResponseDTO aggregateData = devExperienceAggregateService.getAggregateByDevExperienceId(devExperienceId);

        // 2. devExperience 데이터를 기반으로 프롬프트 DTO 생성 및 직렬화
        String prompt = buildPrompt(aggregateData);

        // 3. OpenAIConfig를 통해 GPT API 호출
        String gptResponse = openAIConfig.callGPTApi(prompt);

        // 4. GPT 응답(JSON 문자열)을 ResumeResponseDTO 객체로 파싱
        ResumeResponseDTO resumeResponseDTO;
        try {
            resumeResponseDTO = objectMapper.readValue(gptResponse, ResumeResponseDTO.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.GPT_RESPONSE_PARSE_ERROR);
        }

        // 5. aggregateData에서 조회된 프로젝트들을 경험(experience) 항목으로 매핑
        List<ResumeResponseDTO.Experience> experiences = aggregateData.getProjects().stream()
                .map(project -> ResumeResponseDTO.Experience.builder()
                        .project(project.getTitle())
                        .summary(project.getIntroduce())
                        .position(project.getPosition())
                        .duration(project.getStartDate() + " ~ " + (project.getEndDate() != null ? project.getEndDate().toString() : ""))
                        .skills(Collections.emptyList())
                        .description(project.getContent() != null ? List.of(project.getContent()) : List.of())
                        .build())
                .collect(Collectors.toList());

        // 6. aggregateData에서 조회된 인턴십 데이터를 활동(activities) 항목으로 매핑
        List<ResumeResponseDTO.Activity> activities = aggregateData.getInternships().stream()
                .map(internship -> ResumeResponseDTO.Activity.builder()
                        .title(internship.getCompanyName())
                        .duration(internship.getStartDate() + " ~ " + (internship.getEndDate() != null ? internship.getEndDate().toString() : ""))
                        .description(internship.getContent())
                        .build())
                .collect(Collectors.toList());

        // 7. aggregateData에서 조회된 stack 데이터를 활용하여 기술(skills) 항목 구성 (수준은 빈 문자열로 처리)
        List<ResumeResponseDTO.Skill> skills = aggregateData.getStacks().stream()
                .map(stack -> ResumeResponseDTO.Skill.builder()
                        .position("")  // 필요한 경우 적절한 값을 설정 (예: "Backend" 등)
                        .skills(List.of(stack.getName()))
                        .build())
                .collect(Collectors.toList());

        // 8. 교육(education)은 모든 필드가 빈 문자열로 설정
        ResumeResponseDTO.Education education = ResumeResponseDTO.Education.builder()
                .name("")
                .major("")
                .duration("")
                .build();

        // 9. builder 패턴을 사용하여 최종 ResumeResponseDTO 객체 구성 (사용자 정보는 빈 문자열)
        resumeResponseDTO = ResumeResponseDTO.builder()
                .name("")
                .title("")
                .phone("")
                .email("")
                .github("")
                .blog("")
                .summary(resumeResponseDTO.getSummary())  // GPT 응답에서 파싱된 summary 사용
                .experience(experiences)
                .activities(activities)
                .skills(skills)
                .education(education)
                .build();

        return resumeResponseDTO;
    }

    private String buildPrompt(Object aggregate) {
        String aggregateJson;
        try {
            aggregateJson = objectMapper.writeValueAsString(aggregate);
        } catch (Exception e) {
            aggregateJson = aggregate.toString();
        }

        // ResumePromptDTO를 빌더 패턴으로 생성하여 프롬프트 내용을 구조화
        ResumePromptDTO promptDTO = ResumePromptDTO.builder()
                .header(ResumePrompt.HEADER)
                .jsonTemplate(ResumePrompt.JSON_TEMPLATE)
                .notes(ResumePrompt.NOTES)
                .aggregateData(aggregateJson)
                .build();

        try {
            return objectMapper.writeValueAsString(promptDTO);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}