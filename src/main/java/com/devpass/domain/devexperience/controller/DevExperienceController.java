package com.devpass.domain.devexperience.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.devexperience.dto.request.DevExperienceAddRequestDTO;
import com.devpass.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.domain.devexperience.service.DevExperienceService;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/dev-experiences")
@Tag(name = "개발경험 API", description = "새로운 개발경험 추가 및 조회")
public class DevExperienceController {

	private final DevExperienceService devExperienceService;
	private final DevExperienceAggregateService aggregateService;

	@Operation(
		summary = "devExprience(개발경험) 등록",
		description = "개발 경험 등록 페이지에서 경험 추가할 때 사용"
	)
	@PostMapping
	public ApiResponse<DevExperienceResponseDTO> addDevExperience(
		@RequestBody DevExperienceAddRequestDTO request, @AuthUser Long userId
	) {
		DevExperienceResponseDTO response = devExperienceService.addDevExperience(request);
		return ApiResponse.of(SuccessCode.CREATED, response);
	}

	@Operation(
		summary = "devExprience(개발경험) 리스트 조회",
		description = "개발 경험 리스트 조회"
	)
	@GetMapping
	public ApiResponse<List<DevExperienceResponseDTO>> getAllDevExperiences(@AuthUser Long userId) {
		List<DevExperienceResponseDTO> responses = devExperienceService.getAllDevExperiences();
		return ApiResponse.of(SuccessCode.OK, responses);
	}

	@Operation(
		summary = "devExprience(개발경험) 상세 조회 ",
		description = "개발 경험(프로젝트, 기술스택, 인턴십 경험)을 한 번에 조회"
	)
	@GetMapping("/{devExperienceId}")
	public ApiResponse<DevExperienceAggregateResponseDTO> getAggregateByDevExperienceId(
		@PathVariable("devExperienceId") Long devExperienceId, @AuthUser Long userId) {
		DevExperienceAggregateResponseDTO response = aggregateService.getAggregateByDevExperienceId(devExperienceId);
		return ApiResponse.of(SuccessCode.OK, response);
	}
}
