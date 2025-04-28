package com.devpass.domain.githubinfo.controller;

import com.devpass.domain.githubinfo.dto.GitHubDetailResponseDTO;
import com.devpass.domain.githubinfo.service.GitHubInfoService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
@Slf4j
public class GitHubInfoController {

    private final GitHubInfoService githubInfoService;

    @Operation(summary = "로그인된 사용자의 GitHub 정보 조회")
    @GetMapping("/details")
    public ApiResponse<GitHubDetailResponseDTO> getGitHubDetails(
            @Parameter(hidden = true)
            @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authClient,
            @RequestParam(defaultValue = "6") int maxPinned
    ) {
        String token = authClient.getAccessToken().getTokenValue();
        log.info("▶ 컨트롤러: 액세스 토큰 조회 완료");

        GitHubDetailResponseDTO details = githubInfoService.getGitHubDetails(token, maxPinned);
        return ApiResponse.of(SuccessCode.OK, details);
    }
}
