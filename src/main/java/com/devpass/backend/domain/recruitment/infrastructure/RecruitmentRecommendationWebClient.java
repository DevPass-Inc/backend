package com.devpass.backend.domain.recruitment.infrastructure;

import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
import com.devpass.backend.domain.recruitment.infrastructure.RecruitmentRecommendationClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RecruitmentRecommendationWebClient implements RecruitmentRecommendationClient {

    private final WebClient webClient;

    @Override
    public Mono<List<RecommendRecruitResponseDTO>> getRecommendRecruit(
        RecommendRecruitRequestDTO request) {
        return webClient.post()
            .uri("/recommend")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<RecommendRecruitResponseDTO>>() {});
    }
}
