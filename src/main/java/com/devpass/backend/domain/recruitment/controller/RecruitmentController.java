package com.devpass.backend.domain.recruitment.controller;

import com.devpass.backend.domain.recruitment.converter.RecruitmentConverter;
import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import com.devpass.backend.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.backend.domain.recruitment.entity.Recruitment;
import com.devpass.backend.domain.recruitment.service.RecruitmentService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Mono<CustomResponse<List<RecommendRecruitResponseDTO>>> getRecommendRecruit(
        @RequestBody RecommendRecruitRequestDTO request) {

        return recruitmentService.getRecommendRecruit(request)
            .map(recommendations -> CustomResponse.of(ResultCode.OK, recommendations));
    }

    @Operation(summary = "채용공고 조회", description = "채용공고 개별 조회")
    @GetMapping("/{recruitment_id}")
    public CustomResponse<RecruitmentDetailResponseDTO> getRecruitment(@PathVariable("recruitment_id") Long recruitmentId) {
        Recruitment recruitment = recruitmentService.getRecruitment(recruitmentId);

        RecruitmentDetailResponseDTO recruitmentDetail = RecruitmentConverter.toRecruitmentDetailResponse(recruitment);
        return CustomResponse.of(ResultCode.OK, recruitmentDetail);
    }
}