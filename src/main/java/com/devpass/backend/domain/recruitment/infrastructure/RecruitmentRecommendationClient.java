package com.devpass.backend.domain.recruitment.infrastructure;

import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface RecruitmentRecommendationClient {
    Mono<List<RecommendRecruitResponseDTO>> getRecommendRecruit(RecommendRecruitRequestDTO request);

}
