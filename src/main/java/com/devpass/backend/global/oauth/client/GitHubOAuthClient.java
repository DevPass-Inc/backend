package com.devpass.backend.global.oauth.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.devpass.backend.global.oauth.model.GitHubUserInfo;
import com.devpass.backend.global.oauth.model.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GitHubOAuthClient implements OAuthClient {

	@Value("${oauth.github.client-id}")
	private String clientId;

	@Value("${oauth.github.client-secret}")
	private String clientSecret;

	@Value("${oauth.github.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.github.token-uri}")
	private String tokenUri;

	@Value("${oauth.github.user-info-uri}")
	private String userInfoUri;

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public String getAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("code", code);
		params.add("redirect_uri", redirectUri);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(
			tokenUri,
			request,
			Map.class
		);

		return (String)response.getBody().get("access_token");
	}

	@Override
	public OAuthUserInfo getUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<GitHubUserInfo> response = restTemplate.exchange(
			userInfoUri,
			HttpMethod.GET,
			request,
			GitHubUserInfo.class
		);

		GitHubUserInfo userInfo = response.getBody();

		if (userInfo.getEmail() == null || userInfo.getEmail().isBlank()) {
			String emailUri = userInfoUri + "/emails";

			ResponseEntity<List<Map<String, Object>>> emailResponse = restTemplate.exchange(
				emailUri,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<>() {
				}
			);

			List<Map<String, Object>> emails = emailResponse.getBody();
			if (emails != null) {
				for (Map<String, Object> entry : emails) {
					if (Boolean.TRUE.equals(entry.get("primary"))) {
						userInfo.setEmail((String)entry.get("email"));
						break;
					}
				}
			}
		}

		return userInfo;
	}
}