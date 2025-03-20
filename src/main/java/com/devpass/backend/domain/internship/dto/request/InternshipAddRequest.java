package com.devpass.backend.domain.internship.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InternshipAddRequest {
    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
}