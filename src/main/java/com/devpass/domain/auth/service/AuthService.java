package com.devpass.domain.auth.service;

import com.devpass.domain.auth.dto.request.SignupRequestDTO;
import com.devpass.domain.auth.dto.response.SignupResponseDTO;
import com.devpass.domain.user.entity.User;
import com.devpass.domain.user.repository.UserRepository;
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
