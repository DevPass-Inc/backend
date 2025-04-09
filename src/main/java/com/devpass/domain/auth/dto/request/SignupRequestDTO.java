package com.devpass.domain.auth.dto.request;

public record SignupRequestDTO(
	String email,
	String name,
	String profileImage,
	String authCode
) {
}