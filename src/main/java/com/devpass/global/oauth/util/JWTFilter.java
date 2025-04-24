package com.devpass.global.oauth.util;

import static com.devpass.global.oauth.util.CookieUtil.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devpass.domain.user.dto.UserDTO;
import com.devpass.domain.user.entity.User;
import com.devpass.domain.user.repository.UserRepository;
import com.devpass.global.constants.Constants;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final UserRepository userRepository;

	private static final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return Constants.NO_NEED_FILTER_URLS.stream()
			.anyMatch(pattern -> pathMatcher.match(pattern, uri));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		Map<String, String> tokens = extractTokensFromCookie(request);

		if (tokens.isEmpty() || tokens.get("refreshToken") == null) {
			ErrorResponseUtil.sendErrorResponse(response, ErrorStatus.UNAUTHORIZED);
			return;
		}

		if (tokens.get("accessToken") == null) {
			ErrorResponseUtil.sendErrorResponse(response, ErrorStatus.TOKEN_EXPIRED);
			return;
		}

		if (jwtUtil.isExpired(tokens.get("accessToken"))) {
			if (request.getRequestURI().equals("/api/v1/oauth/reissue")) {
				authenticateUser(tokens.get("refreshToken"));
				filterChain.doFilter(request, response);
				return;
			}

			ErrorResponseUtil.sendErrorResponse(response, ErrorStatus.TOKEN_EXPIRED);
			return;
		}

		authenticateUser(tokens.get("accessToken"));
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token) {
		String providerId = jwtUtil.getProviderId(token);

		User user = userRepository.findByProviderId(providerId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

		UserDTO userDTO = UserDTO.builder()
			.id(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.provider(user.getProvider())
			.providerId(providerId)
			.build();

		CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
		Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null,
			customOAuth2User.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}