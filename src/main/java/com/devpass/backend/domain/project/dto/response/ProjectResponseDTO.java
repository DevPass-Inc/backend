package com.devpass.backend.domain.project.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {
    private Long id;
    private String title;
    private String introduce;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
}
