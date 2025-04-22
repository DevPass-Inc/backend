package com.devpass.global.oauth.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.devpass.domain.user.dto.UserDTO;

public class CustomOAuth2User implements OAuth2User {

	private final UserDTO userDTO;

	public CustomOAuth2User(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	@Override
	public Map<String, Object> getAttributes() {

		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> "ROLE_USER");
	}


	public Long getId() {
		return userDTO.getId();
	}

	public String getName() {
		return userDTO.getName();
	}

	public String getProviderId() {
		return userDTO.getProviderId();
	}

	public String getEmail() {
		return userDTO.getEmail();
	}
}
