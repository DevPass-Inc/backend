package com.devpass.backend.domain.devexperience.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevExperienceAddRequest {
    @Schema(description = "개발경험 명", example = "개발경험1")
    private String title;

    @Schema(description = "개발경험 설명", example = "경험 설명")
    private String description;
}
