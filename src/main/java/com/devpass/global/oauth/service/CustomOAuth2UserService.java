package com.devpass.global.oauth.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.user.dto.UserDTO;
import com.devpass.domain.user.entity.User;
import com.devpass.domain.user.repository.UserRepository;
import com.devpass.global.oauth.dto.CustomOAuth2User;
import com.devpass.global.oauth.dto.GitHubResponseDTO;
import com.devpass.global.oauth.dto.OAuth2Response;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	public CustomOAuth2UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		System.out.println(oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = null;

		if (registrationId.equals("github")) {

			oAuth2Response = new GitHubResponseDTO(oAuth2User.getAttributes());
		} else {
			throw new OAuth2AuthenticationException("Unsupported provider: " + registrationId);
		}

		String providerId = oAuth2Response.getProviderId();

		Optional<User> existData = userRepository.findByProviderId(providerId);

		if (existData.isEmpty()) {

			User userEntity = User.builder()
				.name(oAuth2Response.getName())
				.email(oAuth2Response.getEmail())
				.provider(oAuth2Response.getProvider())
				.providerId(oAuth2Response.getProviderId())
				.build();

			userRepository.save(userEntity);

			UserDTO userDTO = UserDTO.builder()
				.id(userEntity.getId())
				.name(oAuth2Response.getName())
				.email(oAuth2Response.getEmail())
				.provider(oAuth2Response.getProvider())
				.providerId(oAuth2Response.getProviderId())
				.build();

			return new CustomOAuth2User(userDTO);
		} else {

			User user = existData.get();

			userRepository.save(user);

			UserDTO userDTO = UserDTO.builder()
				.id(user.getId())
				.name(oAuth2Response.getName())
				.email(oAuth2Response.getEmail())
				.provider(oAuth2Response.getProvider())
				.providerId(oAuth2Response.getProviderId())
				.build();

			return new CustomOAuth2User(userDTO);
		}
	}
}