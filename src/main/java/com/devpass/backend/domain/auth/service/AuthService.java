package com.devpass.backend.domain.auth.service;

import com.devpass.backend.domain.auth.dto.request.SignupRequestDTO;
import com.devpass.backend.domain.auth.dto.response.SignupResponseDTO;
import com.devpass.backend.domain.user.entity.User;
import com.devpass.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	public SignupResponseDTO signup(SignupRequestDTO dto) {
		User user = userRepository.save(User.builder()
			.email(dto.email())
			.name(dto.name())
			.authCode(dto.authCode())
			.profileImage(dto.profileImage())
			.build());

		return new SignupResponseDTO(user.getId(), user.getName(), user.getEmail());
	}
}
