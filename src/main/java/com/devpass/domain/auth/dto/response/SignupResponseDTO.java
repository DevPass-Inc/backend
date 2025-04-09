package com.devpass.domain.auth.dto.response;

public record SignupResponseDTO(
	Long userId,
	String name,
	String email
) {
}