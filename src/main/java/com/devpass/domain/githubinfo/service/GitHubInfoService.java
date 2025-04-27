package com.devpass.domain.githubinfo.service;

import com.devpass.domain.githubinfo.dto.GitHubDetailResponseDTO;
import com.devpass.domain.githubinfo.dto.PinnedRepo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubInfoService {

    private final WebClient githubWebClient;
    private final WebClient githubGraphQlClient;

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_REF =
            new ParameterizedTypeReference<>() {
            };

    public GitHubDetailResponseDTO getGitHubDetails(String token, int maxPinned) {
        Map<String, Object> userInfo = githubWebClient.get()
                .uri("/user")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(MAP_REF)
                .block();
        String login = (String) userInfo.get("login");
        log.info("▶ 서비스: 사용자 GitHub login = {}", login);

        String profileReadme;
        try {
            profileReadme = githubWebClient.get()
                    .uri("/repos/{owner}/{repo}/readme", login, login)
                    .headers(h -> h.setBearerAuth(token))
                    .accept(MediaType.valueOf("application/vnd.github.raw"))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.warn("프로필 README 없음: {}/{}", login, login);
            profileReadme = "";
        }
        log.info("▶ 서비스: profileReadme length = {}", profileReadme.length());

        String graphQL = """
                {
                  user(login: "%s") {
                    pinnedItems(first: %d) {
                      nodes {
                        ... on Repository {
                          name
                          description
                          object(expression: "HEAD:README.md") {
                            ... on Blob { text }
                          }
                        }
                      }
                    }
                  }
                }
                """.formatted(login, maxPinned);

        Map<String, Object> resp = githubGraphQlClient.post()
                .headers(h -> h.setBearerAuth(token))
                .bodyValue(Map.of("query", graphQL))
                .retrieve()
                .bodyToMono(MAP_REF)
                .block();

        Map<String, Object> data = (Map<String, Object>) resp.get("data");
        Map<String, Object> user = (Map<String, Object>) data.get("user");
        Map<String, Object> pinned = (Map<String, Object>) user.get("pinnedItems");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> nodes = (List<Map<String, Object>>) pinned.get("nodes");

        List<PinnedRepo> repos = nodes.stream().map(repo -> {
            String name = (String) repo.get("name");
            String desc = (String) repo.get("description");
            Map<String, Object> blob = (Map<String, Object>) repo.get("object");
            String readme = blob != null ? (String) blob.get("text") : "";
            log.info("  • repo='{}', hasReadme={}", name, !readme.isBlank());
            return new PinnedRepo(name, desc, readme);
        }).collect(Collectors.toList());

        log.info("▶ 서비스: 총 {}개의 핀된 레포 처리 완료", repos.size());

        return GitHubDetailResponseDTO.builder()
                .login(login)
                .profileReadme(profileReadme)
                .pinnedRepos(repos)
                .build();
    }
}
