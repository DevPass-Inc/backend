package com.devpass.domain.project.service;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.project.converter.ProjectConverter;
import com.devpass.domain.project.dto.request.ProjectAddRequest;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.project.entity.Project;
import com.devpass.domain.project.repository.ProjectRepository;
import com.devpass.global.error.ErrorCode;
import com.devpass.global.error.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DevExperienceRepository devExperienceRepository;


    @Transactional
    public ProjectResponseDTO addProject(Long devExperienceId, ProjectAddRequest request) {
        DevExperience devExperience = devExperienceRepository.findById(devExperienceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        Project project = ProjectConverter.toEntity(request, devExperience);
        Project saved = projectRepository.save(project);
        return ProjectConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        return ProjectConverter.toResponse(project);
    }


    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByDevExperienceId(Long devExperienceId) {
        return projectRepository.findAllByDevExperience_Id(devExperienceId)
                .stream()
                .map(ProjectConverter::toResponse)
                .collect(Collectors.toList());
    }

}
