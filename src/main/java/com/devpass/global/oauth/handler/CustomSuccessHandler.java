package com.devpass.global.oauth.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.devpass.domain.user.entity.User;
import com.devpass.domain.user.repository.UserRepository;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.oauth.dto.TokenDTO;
import com.devpass.global.oauth.util.CookieUtil;
import com.devpass.global.oauth.util.JWTUtil;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final JWTUtil jwtUtil;
	private final String redirectUrl;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public CustomSuccessHandler(UserRepository userRepository, JWTUtil jwtUtil,
		@Value("${app.redirect-url}") String redirectUrl) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.redirectUrl = redirectUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		CustomOAuth2User customUser = (CustomOAuth2User)authentication.getPrincipal();
		User user = findUserByProviderId(customUser.getProviderId());

		handleSuccessLogin(response, user);
	}

	private User findUserByProviderId(String providerId) {
		return userRepository.findByProviderId(providerId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
	}

	private void handleSuccessLogin(HttpServletResponse response, User user) throws IOException {
		TokenDTO tokenDTO = jwtUtil.generateTokens(user.getProviderId());

		long expiration = jwtUtil.getExpiration(tokenDTO.getRefreshToken()).getTime();

		response.addCookie(CookieUtil.createCookie("accessToken", tokenDTO.getAccessToken(), expiration));
		response.addCookie(CookieUtil.createCookie("refreshToken", tokenDTO.getRefreshToken(), expiration));

		response.sendRedirect(redirectUrl);
	}

}