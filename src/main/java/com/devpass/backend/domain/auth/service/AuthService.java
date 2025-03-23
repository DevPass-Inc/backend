package com.devpass.backend.domain.auth.service;

import org.springframework.stereotype.Service;

import com.devpass.backend.domain.user.entity.User;
import com.devpass.backend.domain.user.repository.UserRepository;
import com.devpass.backend.global.oauth.model.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	public User login(OAuthUserInfo userInfo) {
		if (userInfo.getEmail() == null || userInfo.getEmail().isBlank()) {
			throw new IllegalStateException("이메일을 가져올 수 없습니다. GitHub 계정의 이메일이 비공개일 수 있습니다.");
		}

		return userRepository.findByEmail(userInfo.getEmail())
			.orElseGet(() -> userRepository.save(User.builder()
				.authCode(userInfo.getId())
				.email(userInfo.getEmail())
				.name(userInfo.getName())
				.profileImage(userInfo.getProfileImage())
				.build()));
	}
}