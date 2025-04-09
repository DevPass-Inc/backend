package com.devpass.domain.recruitment.infrastructure;

import com.devpass.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface RecruitmentRecommendationClient {
    Mono<List<RecommendRecruitResponseDTO>> getRecommendRecruit(RecommendRecruitRequestDTO request);

}
