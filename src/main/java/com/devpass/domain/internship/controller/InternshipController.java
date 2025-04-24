package com.devpass.domain.internship.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.internship.dto.request.InternshipAddRequestDTO;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.service.InternshipService;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/internships")
@Tag(name = "Internship API", description = "인턴십 경험 관련 API")
public class InternshipController {

	private final InternshipService internshipService;

	@Operation(
		summary = "인턴십 경험 등록",
		description = "개발 경험 등록 페이지 - 인턴십 경험 등록(개발 경험 id를 기준으로 등록)"
	)
	@PostMapping("/{devExperience_id}")
	public ApiResponse<InternshipResponseDTO> addInternship(
		@AuthUser Long userId,
		@PathVariable("devExperience_id") Long devExperienceId,
		@RequestBody InternshipAddRequestDTO request) {
		internshipService.addInternship(userId, devExperienceId, request);
		return ApiResponse.of(SuccessCode.CREATED);
	}

	@DeleteMapping("/{devExperience_id}")
	public ApiResponse<Void> deleteInternships(@PathVariable("devExperience_id") Long devExperienceId) {
		internshipService.deleteInternshipsByDevExperienceId(devExperienceId);
		return ApiResponse.of(SuccessCode.OK);
	}

	@PutMapping("/{internship_id}")
	public ApiResponse<InternshipResponseDTO> updateInternship(
		@PathVariable("internship_id") Long internshipId,
		@RequestBody InternshipAddRequestDTO request) {
		InternshipResponseDTO updated = internshipService.updateInternship(internshipId, request);
		return ApiResponse.of(SuccessCode.OK, updated);
	}

}
