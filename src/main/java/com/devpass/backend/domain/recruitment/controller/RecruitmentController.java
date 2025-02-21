package com.devpass.backend.domain.recruitment.controller;

import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequest;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponse;
import com.devpass.backend.domain.recruitment.service.RecruitmentService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
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
@RequestMapping("/api/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @Operation(summary = "AI 기업 매칭", description = "AI 기업 매칭 조회")
    @PostMapping("/ai")
    public Mono<CustomResponse<List<RecommendRecruitResponse>>> getRecommendRecruit(
        @RequestBody RecommendRecruitRequest request) {

        return recruitmentService.getRecommendRecruit(request)
            .map(recommendations -> CustomResponse.of(ResultCode.OK, recommendations));
    }
}