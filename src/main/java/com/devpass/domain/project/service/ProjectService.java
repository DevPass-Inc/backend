package com.devpass.domain.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.project.converter.ProjectConverter;
import com.devpass.domain.project.dto.request.ProjectAddRequestDTO;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.project.entity.Project;
import com.devpass.domain.project.repository.ProjectRepository;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final DevExperienceRepository devExperienceRepository;

	@Transactional
	public ProjectResponseDTO addProject(Long userId, Long devExperienceId, ProjectAddRequestDTO request) {
		DevExperience devExperience = devExperienceRepository.findByIdAndUserId(devExperienceId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		Project project = ProjectConverter.toEntity(request, devExperience);
		Project saved = projectRepository.save(project);
		return ProjectConverter.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public ProjectResponseDTO getProjectById(Long userId, Long projectId) {
		Project project = projectRepository.findById(projectId)
			.filter(p -> p.getDevExperience().getUser().getId().equals(userId))
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
		return ProjectConverter.toResponse(project);
	}

	@Transactional(readOnly = true)
	public List<ProjectResponseDTO> getProjectsByDevExperienceId(Long userId, Long devExperienceId) {
		DevExperience devExperience = devExperienceRepository.findByIdAndUserId(devExperienceId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		return projectRepository.findAllByDevExperience_Id(devExperience.getId())
			.stream()
			.map(ProjectConverter::toResponse)
			.collect(Collectors.toList());
	}
}