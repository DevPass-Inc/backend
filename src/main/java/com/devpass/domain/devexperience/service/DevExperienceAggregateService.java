package com.devpass.domain.devexperience.service;

import com.devpass.domain.devexpstack.service.DevExpStackService;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.service.InternshipService;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.project.service.ProjectService;
import com.devpass.domain.stack.dto.response.StackResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevExperienceAggregateService {

	private final DevExperienceService devExperienceService;
	private final ProjectService projectService;
	private final InternshipService internshipService;
	private final DevExpStackService devExpStackService;

	@Transactional(readOnly = true)
	public DevExperienceAggregateResponseDTO getAggregateByDevExperienceId(Long userId, Long devExperienceId) {
		DevExperienceResponseDTO devExperience = devExperienceService.getDevExperienceById(userId, devExperienceId);
		List<ProjectResponseDTO> projects = projectService.getProjectsByDevExperienceId(userId, devExperienceId);
		List<InternshipResponseDTO> internships = internshipService.getInternshipsByDevExperienceId(userId,
			devExperienceId);
		List<StackResponseDTO> stacks = devExpStackService.getStacksByDevExperienceId(userId, devExperienceId);
		return new DevExperienceAggregateResponseDTO(devExperience, projects, internships, stacks);
	}
}
