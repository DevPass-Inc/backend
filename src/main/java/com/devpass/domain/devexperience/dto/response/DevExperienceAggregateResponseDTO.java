package com.devpass.domain.devexperience.dto.response;

import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.stack.dto.response.StackResponseDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DevExperienceAggregateResponseDTO {
    private DevExperienceResponseDTO devExperience;
    private List<ProjectResponseDTO> projects;
    private List<InternshipResponseDTO> internships;
    private List<StackResponseDTO> stacks;
}
