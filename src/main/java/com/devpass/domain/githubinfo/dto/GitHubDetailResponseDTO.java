package com.devpass.domain.githubinfo.dto;

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
    private String login;
    private String profileReadme;
    private List<PinnedRepo> pinnedRepos;
}
