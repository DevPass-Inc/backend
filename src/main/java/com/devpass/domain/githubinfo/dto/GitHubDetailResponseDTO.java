package com.devpass.domain.githubinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubDetailResponseDTO {
    @Schema(description = "최대 핀된 레포지토리 개수", example = "6")
    private int maxPinned;
    private String login;
    private String email;
    private String profileUrl;
    private String profileReadme;
    private List<PinnedRepo> pinnedRepos;
}
