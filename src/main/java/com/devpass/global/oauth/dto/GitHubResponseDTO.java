package com.devpass.global.oauth.dto;

import java.util.Map;

public class GitHubResponseDTO implements OAuth2Response {

	private final Map<String, Object> attributes;

	public GitHubResponseDTO(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProvider() {
		return "github";
	}

	@Override
	public String getProviderId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getEmail() {
		Object email = attributes.get("email");
		return email != null ? email.toString() : null;
	}

	@Override
	public String getName() {
		Object name = attributes.get("name");
		return name != null ? name.toString() : null;
	}
}