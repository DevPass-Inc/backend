package com.devpass.backend.domain.resume.service;

import com.devpass.backend.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.backend.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.backend.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.backend.domain.recruitment.service.RecruitmentService;
import com.devpass.backend.domain.resume.document.ResumeDocument;
import com.devpass.backend.domain.resume.dto.ResumePromptDTO;
import com.devpass.backend.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.backend.domain.resume.util.ResumePrompt;
import com.devpass.backend.global.config.OpenAIConfig;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final DevExperienceAggregateService devExperienceAggregateService;
    private final RecruitmentService recruitmentService;
    private final OpenAIConfig openAIConfig;
    private final ObjectMapper objectMapper;
    private final ResumePersistenceService resumePersistenceService;
    // resumeRepository는 조회용 메서드에서 사용되므로 그대로 둡니다.
    // private final ResumeRepository resumeRepository;

    /**
     * devExperienceId와 recruitmentStackId를 기반으로 이력서를 생성하고 DB에 저장한 후,
     * 저장된 ResumeDocument를 반환합니다.
     */
    @Transactional
    public ResumeDocument generateAndSaveResume(Long devExperienceId, Long recruitmentStackId) {
        // 1. 개발 경험 데이터 조회
        DevExperienceAggregateResponseDTO aggregateData = devExperienceAggregateService.getAggregateByDevExperienceId(devExperienceId);

        // 2. 채용 공고 상세 정보 조회 (RecruitmentDetailResponseDTO)
        RecruitmentDetailResponseDTO recruitmentDetail = recruitmentService.getRecruitmentById(recruitmentStackId);
        if (recruitmentDetail == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }

        // 3. 프롬프트 생성 (채용 공고 정보 포함)
        String prompt = buildPrompt(aggregateData, recruitmentDetail);

        // 4. GPT API 호출
        String gptResponse = openAIConfig.callGPTApi(prompt);

        // 5. GPT 응답을 ResumeResponseDTO 객체로 파싱
        ResumeResponseDTO resumeResponseDTO;
        try {
            resumeResponseDTO = objectMapper.readValue(gptResponse, ResumeResponseDTO.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.GPT_RESPONSE_PARSE_ERROR);
        }

        // 6. builder 패턴을 사용하여 사용자 관련 정보 필드는 빈 문자열로 설정
        resumeResponseDTO = ResumeResponseDTO.builder()
                .name("")
                .title("")
                .phone("")
                .email("")
                .github("")
                .blog("")
                .summary(resumeResponseDTO.getSummary())
                .experience(resumeResponseDTO.getExperience())
                .activities(resumeResponseDTO.getActivities())
                .skills(resumeResponseDTO.getSkills())
                .education(resumeResponseDTO.getEducation())
                .build();

        // 7. 이력서를 DB에 저장 후 반환
        ResumeDocument savedDocument = resumePersistenceService.saveResume(resumeResponseDTO);
        return savedDocument;
    }

    private String buildPrompt(Object aggregate, RecruitmentDetailResponseDTO recruitmentDetail) {
        String aggregateJson;
        try {
            aggregateJson = objectMapper.writeValueAsString(aggregate);
        } catch (Exception e) {
            aggregateJson = aggregate.toString();
        }

        String recruitmentJson;
        try {
            recruitmentJson = objectMapper.writeValueAsString(recruitmentDetail);
        } catch (Exception e) {
            recruitmentJson = recruitmentDetail.toString();
        }

        // ResumePromptDTO를 빌더 패턴으로 생성하여 프롬프트 내용을 구조화
        ResumePromptDTO promptDTO = ResumePromptDTO.builder()
                .header(ResumePrompt.HEADER)
                .jsonTemplate(ResumePrompt.JSON_TEMPLATE)
                .notes(ResumePrompt.NOTES + "\n추가 지시: 아래 채용 공고 정보를 반드시 참고하여, 채용 공고에 적합한 이력서를 생성해줘.\n채용 공고 내용: " + recruitmentJson)
                .aggregateData(aggregateJson)
                .build();

        try {
            return objectMapper.writeValueAsString(promptDTO);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ResumeDocument getResumeById(String resumeId) {
        return resumePersistenceService.findById(resumeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESUME_NOT_FOUND));
    }
}
