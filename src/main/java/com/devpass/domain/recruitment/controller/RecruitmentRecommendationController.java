package com.devpass.domain.recruitment.controller;

import com.devpass.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import com.devpass.domain.recruitment.service.RecruitmentRecommendationService;
import com.devpass.global.payload.wrapper.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments/recommendations")
public class RecruitmentRecommendationController {

    private final RecruitmentRecommendationService recruitmentRecommendationService;

    @Operation(summary = "AI 기업 매칭", description = "AI 기업 매칭 조회")
    @PostMapping
    public Mono<ApiResponse<List<RecommendRecruitResponseDTO>>> getRecommendRecruit(
        @RequestBody RecommendRecruitRequestDTO request) {

        return recruitmentRecommendationService.getRecommendRecruit(request)
            .map(recommendations -> ApiResponse.of(SuccessCode.OK, recommendations));
    }
}
