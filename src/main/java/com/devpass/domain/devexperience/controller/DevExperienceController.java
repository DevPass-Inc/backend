package com.devpass.domain.devexperience.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
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
@Tag(name = "개발경험 API")
public class DevExperienceController {

	private final DevExperienceService devExperienceService;
	private final DevExperienceAggregateService aggregateService;

	@Operation(summary = "개발경험 등록")
	@PostMapping
	public ApiResponse<DevExperienceResponseDTO> addDevExperience(
		@AuthUser Long userId,
		@RequestBody DevExperienceAddRequestDTO request) {
		DevExperienceResponseDTO response = devExperienceService.addDevExperience(userId, request);
		return ApiResponse.of(SuccessCode.CREATED, response);
	}

	@Operation(summary = "개발경험 리스트 조회")
	@GetMapping
	public ApiResponse<List<DevExperienceResponseDTO>> getAllDevExperiences(@AuthUser Long userId) {
		List<DevExperienceResponseDTO> responses = devExperienceService.getAllDevExperiences(userId);
		return ApiResponse.of(SuccessCode.OK, responses);
	}

	@Operation(summary = "개발경험 상세 조회")
	@GetMapping("/{devExperienceId}")
	public ApiResponse<DevExperienceAggregateResponseDTO> getAggregateByDevExperienceId(
		@AuthUser Long userId,
		@PathVariable("devExperienceId") Long devExperienceId) {
		DevExperienceAggregateResponseDTO response = aggregateService.getAggregateByDevExperienceId(userId, devExperienceId);
		return ApiResponse.of(SuccessCode.OK, response);
	}

	@Operation(summary = "개발경험 삭제")
	@DeleteMapping("/{devExperienceId}")
	public ApiResponse<Void> deleteDevExperience(
		@AuthUser Long userId,
		@PathVariable("devExperienceId") Long devExperienceId) {
		devExperienceService.deleteDevExperience(userId, devExperienceId);
		return ApiResponse.of(SuccessCode.OK);
	}
}
