package com.devpass.domain.devexperience.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.devexperience.converter.DevExperienceConverter;
import com.devpass.domain.devexperience.dto.request.DevExperienceAddRequestDTO;
import com.devpass.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.user.entity.User;
import com.devpass.domain.user.repository.UserRepository;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevExperienceService {

	private final DevExperienceRepository devExperienceRepository;
	private final UserRepository userRepository;

	@Transactional
	public DevExperienceResponseDTO addDevExperience(Long userId, DevExperienceAddRequestDTO request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		DevExperience devExperience = DevExperienceConverter.toEntity(user, request);

		DevExperience saved = devExperienceRepository.save(devExperience);

		return DevExperienceConverter.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<DevExperienceResponseDTO> getAllDevExperiences(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		return devExperienceRepository.findAllByUser(user)
			.stream()
			.map(DevExperienceConverter::toResponse)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public DevExperienceResponseDTO getDevExperienceById(Long userId, Long id) {
		User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		DevExperience devExperience = devExperienceRepository.findByIdAndUser(id, user)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		return DevExperienceConverter.toResponse(devExperience);
	}

	@Transactional
	public void deleteDevExperience(Long userId, Long devExperienceId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		DevExperience devExperience = devExperienceRepository.findByIdAndUser(devExperienceId, user)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		devExperienceRepository.delete(devExperience);
	}
}
