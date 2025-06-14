package com.devpass.domain.project.service;

import com.devpass.domain.projectstack.converter.ProjectStackConverter;
import com.devpass.domain.projectstack.entity.ProjectStack;
import com.devpass.domain.projectstack.repository.ProjectStackRepository;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.domain.stack.repository.StackRepository;
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
	private final StackRepository stackRepository;
	private final ProjectStackRepository projectStackRepository;

	@Transactional
	public ProjectResponseDTO addProject(Long userId, Long devExperienceId, ProjectAddRequestDTO request) {
		DevExperience devExperience = devExperienceRepository.findByIdAndUserId(devExperienceId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

		Project project = ProjectConverter.toEntity(request, devExperience);
		Project savedProject = projectRepository.save(project);

		List<Stack> stacks = request.getStackIds().stream()
			.map(id -> stackRepository.findById(id)
				.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND)))
			.toList();

		List<ProjectStack> projectStacks = ProjectStackConverter.toProjectStacks(stacks, savedProject);
		projectStackRepository.saveAll(projectStacks);

		return ProjectConverter.toResponse(savedProject);
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
		return projectRepository.findAllByDevExperience_Id(devExperienceId)
			.stream()
			.map(ProjectConverter::toResponse)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteProjectsByDevExperienceId(Long devExperienceId) {
		List<Project> projects = projectRepository.findAllByDevExperience_Id(devExperienceId);
		if (projects.isEmpty()) {
			throw new GeneralException(ErrorStatus.NOT_FOUND);
		}
		projectRepository.deleteAll(projects);
	}

	@Transactional
	public ProjectResponseDTO updateProject(Long projectId, ProjectAddRequestDTO request) {
		Project project = projectRepository.findById(projectId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));
		project.update(request);
		return ProjectConverter.toResponse(project);
	}
}
