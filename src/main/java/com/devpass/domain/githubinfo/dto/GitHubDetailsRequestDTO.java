package com.devpass.domain.githubinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubDetailsRequestDTO {

    @Schema(description = "최대 핀된 레포지토리 개수", example = "6")
    private int maxPinned;
}
