package com.devpass.backend.domain.project.dto.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ProjectAddRequest {
    private String projectName;
    private String introduce;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
}
