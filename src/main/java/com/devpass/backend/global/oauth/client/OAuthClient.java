package com.devpass.backend.global.oauth.client;

import com.devpass.backend.global.oauth.model.OAuthUserInfo;

public interface OAuthClient {
	String getAccessToken(String code);

	OAuthUserInfo getUserInfo(String accessToken);
}