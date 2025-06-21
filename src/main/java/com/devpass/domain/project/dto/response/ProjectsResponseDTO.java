package com.devpass.domain.project.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectsResponseDTO {
    private List<ProjectAddResponseDTO> projects;
}
