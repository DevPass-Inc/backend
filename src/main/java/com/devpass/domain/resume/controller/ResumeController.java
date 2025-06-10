package com.devpass.domain.resume.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.service.ResumeService;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/resumes")
@Tag(name = "이력서 API")
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

	private final ResumeService resumeService;

	@GetMapping("/generate/{devExperienceId}/{recruitmentStackId}")
	public ApiResponse<ResumeDocument> generateResume(
			@RegisteredOAuth2AuthorizedClient("github")
			OAuth2AuthorizedClient authClient,
			@AuthenticationPrincipal CustomOAuth2User principal,
			@PathVariable Long devExperienceId,
			@PathVariable Long recruitmentStackId,
			@RequestParam(defaultValue = "true") boolean includeGitHub
	) {
		String githubToken   = authClient.getAccessToken().getTokenValue();
		String providerId    = principal.getProviderId();
		log.info("▶ ResumeController: token={}, providerId={}, devExp={}, recStack={}, includeGitHub={}",
				githubToken, providerId, devExperienceId, recruitmentStackId, includeGitHub);

		ResumeDocument resume = resumeService.generateAndSaveResume(
				githubToken,
				providerId,
				devExperienceId,
				recruitmentStackId,
				includeGitHub
		);
		return ApiResponse.of(SuccessCode.OK, resume);
	}

	@Operation(summary = "이력서 상세 조회")
	@GetMapping("/{resumeId}")
	public ApiResponse<ResumeDocument> getResume(
		@PathVariable("resumeId") String resumeId) {
		ResumeDocument resume = resumeService.getResumeById(resumeId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}

	@Operation(summary = "이력서 리스트 조회")
	@GetMapping()
	public ApiResponse<List<ResumeDocument>> getResumesByUserId(
		@AuthUser Long userId) {
		List<ResumeDocument> resumes = resumeService.getResumesByUserId(userId);
		return ApiResponse.of(SuccessCode.OK, resumes);
	}
}
