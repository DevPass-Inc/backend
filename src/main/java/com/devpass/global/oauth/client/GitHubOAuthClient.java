package com.devpass.global.oauth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class GitHubOAuthClient implements OAuthClient {

	@Value("${oauth.github.client-id}")
	private String clientId;

	@Value("${oauth.github.client-secret}")
	private String clientSecret;

	@Value("${oauth.github.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.github.token-uri}")
	private String tokenUri;

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
		System.out.println("GitHub token response: " + response.getBody());

		return (String) response.getBody().get("access_token");
	}
}