package com.devpass.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
	private Long id;
	private String name;
	private String email;
	private String provider;
	private String providerId;
}