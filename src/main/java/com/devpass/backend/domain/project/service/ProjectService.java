package com.devpass.backend.domain.project.service;

import com.devpass.backend.domain.devexperience.entity.DevExperience;
import com.devpass.backend.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.backend.domain.project.converter.ProjectConverter;
import com.devpass.backend.domain.project.dto.request.ProjectAddRequest;
import com.devpass.backend.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.backend.domain.project.entity.Project;
import com.devpass.backend.domain.project.repository.ProjectRepository;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
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
    public Project addProject(Long devExperienceId, ProjectAddRequest request) {
        DevExperience devExperience = devExperienceRepository.findById(devExperienceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        Project project = ProjectConverter.toEntity(request, devExperience);
        return projectRepository.save(project);
    }


    @Transactional(readOnly = true)
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsByDevExperienceId(Long devExperienceId) {
        return projectRepository.findAllByDevExperience_Id(devExperienceId)
                .stream()
                .map(ProjectConverter::toResponse)
                .collect(Collectors.toList());
    }

}
