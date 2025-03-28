package com.devpass.backend.domain.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectAddRequest {
    @Schema(description = "프로젝트 명", example = "devPass")
    private String title;

    @Schema(description = "프로젝트 개요(간단한 설명)", example = "개발자를 위한 이력서 생성 및 기업 자동 매칭 서비스")
    private String introduce;

    @Schema(description = "담당 분야", example = "backend")
    private String position;

    @Schema(description = "프로젝트 기간 - 시작일")
    private LocalDate startDate;

    @Schema(description = "프로젝트 기간 - 종료일")
    private LocalDate endDate;

    @Schema(description = "구현 내용", example = "openai Api와 github api, 사용자의 경험 정보를 통한 이력서 자동 생성 기능 구현")
    private String content;
}
