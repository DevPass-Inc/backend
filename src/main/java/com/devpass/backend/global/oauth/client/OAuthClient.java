package com.devpass.backend.global.oauth.client;

public interface OAuthClient {
	String getAccessToken(String code);
}