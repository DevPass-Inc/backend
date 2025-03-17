package com.devpass.backend.domain.recruitment.service;

import com.devpass.backend.domain.recruitment.entity.Recruitment;
import com.devpass.backend.domain.recruitment.exception.RecruitmentNotFoundException;
import com.devpass.backend.domain.recruitment.repository.RecruitmentRepository;
import com.devpass.backend.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.backend.domain.recruitment.dto.response.RecommendRecruitResponseDTO;
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
    private final RecruitmentRepository recruitmentRepository;

    // 추천 채용공고 조회 (AI 서버 연결)
    public Mono<List<RecommendRecruitResponseDTO>> getRecommendRecruit(RecommendRecruitRequestDTO request) {
        return webClient.post()
            .uri("/recommend")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<RecommendRecruitResponseDTO>>() {});
    }

    // 채용공고 개별 조회
    public Recruitment getRecruitment(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow(RecruitmentNotFoundException::new);

        return recruitment;
    }
}
