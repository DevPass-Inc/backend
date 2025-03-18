package com.devpass.backend.domain.recruitment.service;

import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import com.devpass.backend.domain.recruitment.infrastructure.RecruitmentRecommendationClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RecruitmentRecommendationService {
    private final RecruitmentRecommendationClient recommendationClient;

    // 추천 채용 공고 조회 (AI 서버 연결)
    public Mono<List<RecommendRecruitResponseDTO>> getRecommendRecruit(
        RecommendRecruitRequestDTO request) {
        return recommendationClient.getRecommendRecruit(request);
    }
}
