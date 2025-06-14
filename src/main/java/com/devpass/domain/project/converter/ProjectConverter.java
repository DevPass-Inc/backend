package com.devpass.domain.project.converter;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.project.dto.request.ProjectAddRequestDTO;
import com.devpass.domain.project.dto.response.ProjectAddResponseDto;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.project.entity.Project;
import com.devpass.domain.projectstack.entity.ProjectStack;
import com.devpass.domain.stack.dto.response.StackResponseDTO;
import com.devpass.domain.stack.entity.Stack;
import java.util.List;

public class ProjectConverter {
    public static Project toEntity(ProjectAddRequestDTO request, DevExperience devExperience) {
        return Project.builder()
                .devExperience(devExperience)
                .title(request.getTitle())
                .introduce(request.getIntroduce())
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .content(request.getContent())
                .build();
    }

    public static ProjectAddResponseDto toResponse(Project project) {
        return new ProjectAddResponseDto(
                project.getId(),
                project.getTitle(),
                project.getIntroduce(),
                project.getPosition(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContent()
        );
    }

    public static ProjectResponseDTO toResponseDto(Project project) {
        List<StackResponseDTO> stacks = project.getProjectStacks().stream()
            .map(ProjectStack::getStack)
            .map(stack -> new StackResponseDTO(stack.getId(), stack.getName()))
            .toList();

        return new ProjectResponseDTO(
            project.getId(),
            project.getTitle(),
            project.getIntroduce(),
            project.getPosition(),
            project.getStartDate(),
            project.getEndDate(),
            project.getContent(),
            stacks
        );
    }
}
