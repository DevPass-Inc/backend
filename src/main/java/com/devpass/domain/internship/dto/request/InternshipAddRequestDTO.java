package com.devpass.domain.internship.dto.request;

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
public class InternshipAddRequestDTO {
    @Schema(description = "인턴십 회사명", example = "Toss bank")
    private String companyName;

    @Schema(description = "인턴십 직무", example = "커피머신")
    private String position;

    @Schema(description = "기간 - 시작일")
    private LocalDate startDate;

    @Schema(description = "기간 - 종료일")
    private LocalDate endDate;

    @Schema(description = "구현 내용", example = "커피머신 청소")
    private String content;
}