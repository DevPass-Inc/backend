package com.devpass.global.oauth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.global.annotation.AuthUser;
import com.devpass.global.oauth.service.CustomOAuth2UserService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuthController {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Operation(summary = "토큰 재발급", description = "AccessToken 만료 시 RefreshToken을 이용한 재발급 요청")
	@PostMapping("/reissue")
	public ApiResponse<String> reissue(HttpServletResponse response, @AuthUser Long userId) {
		customOAuth2UserService.reissue(userId, response);
		return ApiResponse.of(SuccessCode.OK, "토큰 재발급 완료");
	}
}