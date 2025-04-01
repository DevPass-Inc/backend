package com.devpass.backend.domain.devexperience.service;

import com.devpass.backend.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.backend.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.backend.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.backend.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.backend.domain.project.service.ProjectService;
import com.devpass.backend.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.backend.domain.stack.service.StackService;
import com.devpass.backend.domain.internship.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevExperienceAggregateService {

    private final DevExperienceService devExperienceService;
    private final ProjectService projectService;
    private final InternshipService internshipService;
    private final StackService stackService;

    @Transactional(readOnly = true)
    public DevExperienceAggregateResponseDTO getAggregateByDevExperienceId(Long devExperienceId) {
        DevExperienceResponseDTO devExperience = devExperienceService.getDevExperienceById(devExperienceId);
        List<ProjectResponseDTO> projects = projectService.getProjectsByDevExperienceId(devExperienceId);
        List<InternshipResponseDTO> internships = internshipService.getInternshipsByDevExperienceId(devExperienceId);
        List<StackStatusResponseDTO> stacks = stackService.getStacksByDevExperienceId(devExperienceId);
        return new DevExperienceAggregateResponseDTO(devExperience, projects, internships, stacks);
    }
}
