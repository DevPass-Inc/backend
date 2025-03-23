package com.devpass.backend.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.backend.domain.auth.dto.response.LoginResponseDTO;
import com.devpass.backend.domain.auth.service.AuthService;
import com.devpass.backend.domain.user.entity.User;
import com.devpass.backend.global.oauth.client.OAuthClient;
import com.devpass.backend.global.oauth.model.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final OAuthClient oAuthClient;

	@GetMapping("/login/github/callback")
	public ResponseEntity<LoginResponseDTO> githubCallback(@RequestParam("code") String code) {
		String accessToken = oAuthClient.getAccessToken(code);
		OAuthUserInfo userInfo = oAuthClient.getUserInfo(accessToken);
		User user = authService.login(userInfo);

		return ResponseEntity.ok(new LoginResponseDTO(
			user.getName(),
			user.getEmail(),
			accessToken
		));
	}
}