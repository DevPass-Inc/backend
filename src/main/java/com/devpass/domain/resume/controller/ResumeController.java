package com.devpass.domain.resume.controller;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.service.ResumeService;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

	private final ResumeService resumeService;

	/**
	 * 생성 및 저장 API: 로그인된 사용자의 providerId, devExperience_id, recruitmentStack_id 를 받아 이력서를 생성·저장
	 */
	@Operation(summary = "이력서 생성 및 저장")
	@GetMapping("/generate/{devExperience_id}/{recruitmentStack_id}")
	public ApiResponse<ResumeDocument> generateResume(
			@Parameter(hidden = true)
			@AuthenticationPrincipal CustomOAuth2User principal,

			@PathVariable("devExperience_id") Long devExperienceId,
			@PathVariable("recruitmentStack_id") Long recruitmentStackId,

			@RequestParam(name = "includeGitHub", defaultValue = "true")
			boolean includeGitHub
	) {
		String providerId = principal.getProviderId();
		log.info("▶ ResumeController: providerId={}, devExpId={}, recStackId={}, includeGitHub={}",
				providerId, devExperienceId, recruitmentStackId, includeGitHub);

		ResumeDocument resume = resumeService.generateAndSaveResume(
				providerId,
				devExperienceId,
				recruitmentStackId,
				includeGitHub
		);
		return ApiResponse.of(SuccessCode.OK, resume);
	}

	/**
	 * 조회 API:
	 *   저장된 이력서를 resume_id 로 조회
	 */
	@Operation(summary = "이력서 조회")
	@GetMapping("/{resume_id}")
	public ApiResponse<ResumeDocument> getResume(
			@PathVariable("resume_id") String resumeId
	) {
		ResumeDocument resume = resumeService.getResumeById(resumeId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}
}
