package com.devpass.domain.resume.controller;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.service.ResumeService;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.payload.ApiResponse;       // 페이로드용 클래스
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
@Tag(name = "Resume API", description = "이력서 생성 및 조회 API")
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

	private final ResumeService resumeService;

	@Operation(
			summary = "이력서 생성 및 저장",
			description = "로그인된 사용자의 개발 경험(devExperience_id)과 채용 공고(recruitmentStack_id)를 기반으로 이력서를 생성·저장합니다."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
					responseCode = "200",
					description = "이력서 생성 및 저장 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ResumeDocument.class)
					)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "리소스(사용자/개발경험/채용공고)를 찾을 수 없음"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/generate/{devExperience_id}/{recruitmentStack_id}")
	public ApiResponse<ResumeDocument> generateResume(
			@Parameter(hidden = true)
			@AuthenticationPrincipal CustomOAuth2User principal,

			@Parameter(
					name = "devExperience_id",
					description = "개발 경험 식별자 (DevExperience PK)",
					required = true,
					in = ParameterIn.PATH
			)
			@PathVariable("devExperience_id") Long devExperienceId,

			@Parameter(
					name = "recruitmentStack_id",
					description = "채용 공고 스택 식별자 (RecruitmentStack PK)",
					required = true,
					in = ParameterIn.PATH
			)
			@PathVariable("recruitmentStack_id") Long recruitmentStackId,

			@Parameter(
					name = "includeGitHub",
					description = "GitHub 정보 포함 여부 (true/false)",
					example = "true",
					required = false,
					in = ParameterIn.QUERY
			)
			@RequestParam(name = "includeGitHub", defaultValue = "true") boolean includeGitHub
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

	@Operation(
			summary = "이력서 조회",
			description = "저장된 이력서를 resume_id로 조회합니다."
	)
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
					responseCode = "200",
					description = "이력서 조회 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ResumeDocument.class)
					)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 ID의 이력서를 찾을 수 없음"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@GetMapping("/{resume_id}")
	public ApiResponse<ResumeDocument> getResume(
			@Parameter(
					name = "resume_id",
					description = "조회할 이력서 식별자",
					required = true,
					in = ParameterIn.PATH,
					schema = @Schema(type = "string", example = "680e0e807d3fd935de698c22")
			)
			@PathVariable("resume_id") String resumeId
	) {
		ResumeDocument resume = resumeService.getResumeById(resumeId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}
}
