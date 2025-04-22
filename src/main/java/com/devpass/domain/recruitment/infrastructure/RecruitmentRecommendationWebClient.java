package com.devpass.domain.recruitment.infrastructure;

import com.devpass.domain.recruitment.dto.request.RecommendRecruitRequestDTO;
import com.devpass.domain.recruitment.dto.response.RecommendRecruitResponseDTO;

import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RecruitmentRecommendationWebClient implements RecruitmentRecommendationClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<List<RecommendRecruitResponseDTO>> getRecommendRecruit(
        RecommendRecruitRequestDTO request) {
        return webClient.post()
            .uri("/recommend")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .map(response -> {
                Object data = response.get("data");
                if (data instanceof List<?> list) {
                    return list.stream()
                        .map(item -> objectMapper.convertValue(item,
                            RecommendRecruitResponseDTO.class))
                        .collect(Collectors.toList());
                } else {
                    throw new GeneralException(ErrorCode.BAD_REQUEST);
                }
            });
    }
}
