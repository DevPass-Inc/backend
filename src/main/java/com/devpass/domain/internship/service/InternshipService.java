package com.devpass.domain.internship.service;

import java.util.List;
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
	public InternshipResponseDTO getInternshipById(Long userId, Long id) {
		Internship internship = internshipRepository.findByIdAndDevExperience_User_Id(id, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
		return InternshipConverter.toResponse(internship);
	}

	@Transactional(readOnly = true)
	public List<InternshipResponseDTO> getInternshipsByDevExperienceId(Long devExperienceId) {
		return internshipRepository.findAllByDevExperience_Id(devExperienceId)
			.stream()
			.map(InternshipConverter::toResponse)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteInternshipsByDevExperienceId(Long devExperienceId) {
		List<Internship> internships = internshipRepository.findAllByDevExperience_Id(devExperienceId);
		if (internships.isEmpty()) {
			throw new GeneralException(ErrorStatus.NOT_FOUND);
		}
		internshipRepository.deleteAll(internships);
	}

	@Transactional
	public InternshipResponseDTO updateInternship(Long internshipId, InternshipAddRequestDTO request) {
		Internship internship = internshipRepository.findById(internshipId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
		internship.update(request);
		return InternshipConverter.toResponse(internship);
	}
}
