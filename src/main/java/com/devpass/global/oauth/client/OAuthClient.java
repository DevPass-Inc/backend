package com.devpass.global.oauth.client;

public interface OAuthClient {
	String getAccessToken(String code);
}