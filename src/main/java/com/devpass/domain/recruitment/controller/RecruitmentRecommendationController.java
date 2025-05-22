package com.devpass.domain.recruitment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import com.devpass.domain.recruitment.service.RecruitmentRecommendationService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments/recommendations")
@Tag(name = "기업매칭 API")
public class RecruitmentRecommendationController {

	private final RecruitmentRecommendationService recruitmentRecommendationService;

	@Operation(summary = "AI 기업 매칭")
	@PostMapping
	public Mono<ApiResponse<List<RecommendRecruitResponseDTO>>> getRecommendRecruit(
		@RequestBody RecommendRecruitRequestDTO request) {
		return recruitmentRecommendationService.getRecommendRecruit(request)
			.map(recommendations -> ApiResponse.of(SuccessCode.OK, recommendations));
	}
}
