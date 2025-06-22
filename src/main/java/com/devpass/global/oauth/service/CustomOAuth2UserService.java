package com.devpass.global.oauth.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.user.dto.UserDTO;
import com.devpass.domain.user.entity.User;
import com.devpass.domain.user.repository.UserRepository;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.oauth.dto.GitHubResponseDTO;
import com.devpass.global.oauth.dto.OAuth2Response;
import com.devpass.global.oauth.dto.TokenDTO;
import com.devpass.global.oauth.util.CookieUtil;
import com.devpass.global.oauth.util.JWTUtil;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final JWTUtil jwtUtil;

	public CustomOAuth2UserService(UserRepository userRepository, JWTUtil jwtUtil) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = null;

		if (registrationId.equals("github")) {

			oAuth2Response = new GitHubResponseDTO(oAuth2User.getAttributes());
		} else {
			throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
		}

		String providerId = oAuth2Response.getProviderId();
		String email = oAuth2Response.getEmail();

		if (email == null || email.isBlank()) {
			String accessToken = userRequest.getAccessToken().getTokenValue();
			email = fetchPrimaryEmailFromGitHub(accessToken);
		}

		Optional<User> existData = userRepository.findByProviderId(providerId);

		if (existData.isEmpty()) {

			User userEntity = User.builder()
				.name(oAuth2Response.getName())
				.email(email)
				.provider(oAuth2Response.getProvider())
				.providerId(oAuth2Response.getProviderId())
				.build();

			userRepository.save(userEntity);

			UserDTO userDTO = UserDTO.builder()
				.id(userEntity.getId())
				.name(oAuth2Response.getName())
				.email(oAuth2Response.getEmail())
				.provider(oAuth2Response.getProvider())
				.providerId(oAuth2Response.getProviderId())
				.build();

			return new CustomOAuth2User(userDTO);
		} else {

			User user = existData.get();
			user.updateName(oAuth2Response.getName());
			user.updateEmail(oAuth2Response.getEmail());

			userRepository.save(user);
			userRepository.flush();

			UserDTO userDTO = UserDTO.builder()
				.id(user.getId())
				.name(oAuth2Response.getName())
				.email(oAuth2Response.getEmail())
				.provider(oAuth2Response.getProvider())
				.providerId(oAuth2Response.getProviderId())
				.build();

			return new CustomOAuth2User(userDTO);
		}
	}

	@Transactional
	public TokenDTO reissue(Long userId, HttpServletResponse response) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

		TokenDTO tokenDTO = jwtUtil.generateTokens(user.getProviderId());
		long expiration = jwtUtil.getExpiration(tokenDTO.getRefreshToken()).getTime();

		response.addCookie(CookieUtil.createCookie("accessToken", tokenDTO.getAccessToken(), expiration));

		return tokenDTO;
	}

	private String fetchPrimaryEmailFromGitHub(String accessToken) {
		try {
			URL url = new URL("https://api.github.com/user/emails");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);
			connection.setRequestProperty("Accept", "application/vnd.github+json");

			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				List<Map<String, Object>> emails = objectMapper.readValue(connection.getInputStream(), List.class);
				for (Map<String, Object> emailObj : emails) {
					Boolean primary = (Boolean) emailObj.get("primary");
					Boolean verified = (Boolean) emailObj.get("verified");
					if (Boolean.TRUE.equals(primary) && Boolean.TRUE.equals(verified)) {
						return emailObj.get("email").toString();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}