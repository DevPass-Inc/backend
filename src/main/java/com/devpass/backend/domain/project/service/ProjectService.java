package com.devpass.backend.domain.project.service;

import com.devpass.backend.domain.project.converter.ProjectConverter;
import com.devpass.backend.domain.project.dto.request.ProjectAddRequest;
import com.devpass.backend.domain.project.entity.Project;
import com.devpass.backend.domain.project.repository.ProjectRepository;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project addProject(ProjectAddRequest request) {
        Project project = ProjectConverter.toEntity(request);
        return projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
    }
}
