package com.devpass.backend.domain.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanyDetailResponseDTO {

    private Long companyId;

    private String name;

    private String category;

    private String location;

    private String avgSalary;

    private String newHireAvgSalary;

    private Integer employeeCount;

    private String ceoName;

    private String companyHistory;
}
