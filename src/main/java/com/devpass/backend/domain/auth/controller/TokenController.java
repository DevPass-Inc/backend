package com.devpass.backend.domain.auth.controller;

import com.devpass.backend.domain.auth.dto.request.GitHubAccessTokenRequestDTO;
import com.devpass.backend.domain.auth.dto.response.GitHubAccessTokenResponseDTO;
import com.devpass.backend.global.oauth.client.OAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/github")
public class TokenController {

	private final OAuthClient oAuthClient;

	@PostMapping("/token")
	public ResponseEntity<GitHubAccessTokenResponseDTO> getAccessToken(
		@RequestBody GitHubAccessTokenRequestDTO request
	) {
		String accessToken = oAuthClient.getAccessToken(request.code());
		return ResponseEntity.ok(new GitHubAccessTokenResponseDTO(accessToken));
	}
}