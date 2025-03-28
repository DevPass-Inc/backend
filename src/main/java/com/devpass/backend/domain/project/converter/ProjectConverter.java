package com.devpass.backend.domain.project.converter;

import com.devpass.backend.domain.devexperience.entity.DevExperience;
import com.devpass.backend.domain.project.dto.request.ProjectAddRequest;
import com.devpass.backend.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.backend.domain.project.entity.Project;

public class ProjectConverter {
    public static Project toEntity(ProjectAddRequest request, DevExperience devExperience) {
        return Project.builder()
                .devExperience(devExperience)
                .title(request.getProjectName())
                .introduce(request.getIntroduce())
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .content(request.getContent())
                .build();
    }

    public static ProjectResponseDTO toResponse(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getIntroduce(),
                project.getPosition(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContent()
        );
    }
}
