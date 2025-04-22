package com.devpass.domain.resume.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.dto.ResumePromptDTO;
import com.devpass.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.domain.resume.repository.ResumeRepository;
import com.devpass.domain.resume.util.ResumePrompt;
import com.devpass.global.config.OpenAIConfig;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {

	private final DevExperienceAggregateService devExperienceAggregateService;
	private final OpenAIConfig openAIConfig;
	private final ObjectMapper objectMapper;
	private final ResumeRepository resumeRepository;
	private final ResumePersistenceService resumePersistenceService;

	@Transactional
	public ResumeDocument generateAndSaveResume(Long userId, Long devExperienceId) {
		// 1. 개발 경험 데이터(프로젝트, 인턴십, 기술 스택 등)를 조회
		DevExperienceAggregateResponseDTO aggregateData =
			devExperienceAggregateService.getAggregateByDevExperienceId(userId, devExperienceId);

		// 2. devExperience 데이터를 기반으로 프롬프트 DTO 생성 및 직렬화
		String prompt = buildPrompt(aggregateData);

		// 3. OpenAIConfig를 통해 GPT API 호출
		String gptResponse = openAIConfig.callGPTApi(prompt);

		// 4. GPT 응답(JSON 문자열)을 ResumeResponseDTO 객체로 파싱
		ResumeResponseDTO resumeResponseDTO;
		try {
			resumeResponseDTO = objectMapper.readValue(gptResponse, ResumeResponseDTO.class);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.GPT_RESPONSE_PARSE_ERROR);
		}

		// 5. builder 패턴을 사용하여 사용자 관련 정보 필드는 빈 문자열로 설정
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

		// 6. 생성된 이력서를 DB에 저장 (ID가 자동 할당됨)
		ResumeDocument savedDocument = resumePersistenceService.saveResume(userId, resumeResponseDTO);
		return savedDocument;
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
			throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional(readOnly = true)
	public ResumeDocument getResumeById(Long userId, String resumeId) {
		return resumeRepository.findById(resumeId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.RESUME_NOT_FOUND));
	}
}