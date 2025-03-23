package com.devpass.backend.global.oauth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GitHubUserInfo implements OAuthUserInfo {
	private String id;
	private String login;
	private String email;
	private String name;
	private String avatar_url;

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getProfileImage() {
		return avatar_url;
	}
}