package com.devpass.backend.domain.auth.dto.response;

public record SignupResponseDTO(
	Long userId,
	String name,
	String email
) {
}