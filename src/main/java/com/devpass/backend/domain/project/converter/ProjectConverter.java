package com.devpass.backend.domain.project.converter;

import com.devpass.backend.domain.project.dto.request.ProjectAddRequest;
import com.devpass.backend.domain.project.entity.Project;

public class ProjectConverter {
    public static Project toEntity(ProjectAddRequest request) {
        return Project.builder()
                .title(request.getProjectName())
                .introduce(request.getIntroduce())
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .content(request.getContent())
                .build();
    }
}
