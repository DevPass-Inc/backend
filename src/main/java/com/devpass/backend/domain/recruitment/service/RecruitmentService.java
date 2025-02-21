package com.devpass.backend.domain.recruitment.service;

import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequest;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final WebClient webClient;

    public Mono<List<RecommendRecruitResponse>> getRecommendRecruit(RecommendRecruitRequest request) {
        return webClient.post()
            .uri("/recommend")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<RecommendRecruitResponse>>() {});
    }
}
