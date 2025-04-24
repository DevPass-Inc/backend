package com.devpass.domain.recruitment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.domain.recruitment.service.RecruitmentService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments")
public class RecruitmentController {

	private final RecruitmentService recruitmentService;

	@Operation(summary = "채용공고 조회", description = "채용공고 개별 조회")
	@GetMapping("/{recruitmentId}")
	public ApiResponse<RecruitmentDetailResponseDTO> getRecruitment(@PathVariable Long recruitmentId) {
		RecruitmentDetailResponseDTO responseDTO = recruitmentService.getRecruitmentById(recruitmentId);

		return ApiResponse.of(SuccessCode.OK, responseDTO);
	}
}