package com.devpass.domain.devexperience.service;

import com.devpass.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.project.service.ProjectService;
import com.devpass.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.domain.stack.service.StackService;
import com.devpass.domain.internship.service.InternshipService;
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
