package com.devpass.backend.domain.auth.dto.response;

public record LoginResponseDTO(
	String name,
	String email,
	String accessToken
) {
}