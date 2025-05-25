package com.devpass.domain.internship.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "인턴십 API")
public class InternshipController {

	private final InternshipService internshipService;

	@Operation(summary = "인턴십 경험 등록")
	@PostMapping("/{devExperienceId}")
	public ApiResponse<InternshipResponseDTO> addInternship(
		@AuthUser Long userId,
		@PathVariable("devExperienceId") Long devExperienceId,
		@RequestBody InternshipAddRequestDTO request) {
		internshipService.addInternship(userId, devExperienceId, request);
		return ApiResponse.of(SuccessCode.CREATED);
	}

	@Operation(summary = "인턴십 경험 삭제")
	@DeleteMapping("/{internshipId}")
	public ApiResponse<Void> deleteInternships(
		@AuthUser Long userId,
		@PathVariable("internshipId") Long internshipId) {
		internshipService.deleteInternshipById(userId, internshipId);
		return ApiResponse.of(SuccessCode.OK);
	}

	@Operation(summary = "인턴십 경험 수정")
	@PutMapping("/{internshipId}")
	public ApiResponse<InternshipResponseDTO> updateInternship(
		@PathVariable("internshipId") Long internshipId,
		@RequestBody InternshipAddRequestDTO request) {
		InternshipResponseDTO updated = internshipService.updateInternship(internshipId, request);
		return ApiResponse.of(SuccessCode.OK, updated);
	}

	@Operation(summary = "인턴십 경험 목록 조회")
	@GetMapping("/{devExperienceId}")
	public ApiResponse<List<InternshipResponseDTO>> getInternshipsByDevExperienceId(
		@AuthUser Long userId,
		@PathVariable("devExperienceId") Long devExperienceId) {
		return ApiResponse.of(SuccessCode.OK, internshipService.getInternshipsByDevExperienceId(userId, devExperienceId));
	}

}
