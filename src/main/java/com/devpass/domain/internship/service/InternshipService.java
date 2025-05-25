package com.devpass.domain.internship.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.internship.converter.InternshipConverter;
import com.devpass.domain.internship.dto.request.InternshipAddRequestDTO;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.entity.Internship;
import com.devpass.domain.internship.repository.InternshipRepository;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternshipService {

	private final InternshipRepository internshipRepository;
	private final DevExperienceRepository devExperienceRepository;

	@Transactional
	public InternshipResponseDTO addInternship(Long userId, Long devExperienceId, InternshipAddRequestDTO request) {
		DevExperience devExperience = devExperienceRepository.findByIdAndUserId(devExperienceId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
		Internship internship = InternshipConverter.toEntity(request, devExperience);
		Internship saved = internshipRepository.save(internship);
		return InternshipConverter.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<InternshipResponseDTO> getInternshipsByDevExperienceId(Long userId, Long devExperienceId) {
		return internshipRepository.findAllByDevExperience_Id(devExperienceId)
			.stream()
			.map(InternshipConverter::toResponse)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteInternshipById(Long userId, Long internshipId) {
		Optional<Internship> internship = internshipRepository.findById(internshipId);
		if (internship.isEmpty()) {
			throw new GeneralException(ErrorStatus.NOT_FOUND);
		}
		if (!internship.get().getDevExperience().getUser().getId().equals(userId)) {
			throw new GeneralException(ErrorStatus.UNAUTHORIZED);
		}
		internshipRepository.deleteById(internshipId);
	}

	@Transactional
	public InternshipResponseDTO updateInternship(Long userId, Long internshipId, InternshipAddRequestDTO request) {
		Internship internship = internshipRepository.findById(internshipId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
		if (!internship.getDevExperience().getUser().getId().equals(userId)) {
			throw new GeneralException(ErrorStatus.UNAUTHORIZED);
		}
		internship.update(request);
		return InternshipConverter.toResponse(internship);
	}
}
