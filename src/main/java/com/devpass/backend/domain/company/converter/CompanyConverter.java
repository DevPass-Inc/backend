package com.devpass.backend.domain.company.converter;

import com.devpass.backend.domain.company.dto.response.CompanyDetailResponseDTO;
import com.devpass.backend.domain.company.entity.Company;

public class CompanyConverter {
    public static CompanyDetailResponseDTO toCompanyDetailResponse(Company company) {
        return CompanyDetailResponseDTO.builder()
                .companyId(company.getId())
                .name(company.getName())
                .category(company.getCategory())
                .location(company.getLocation())
                .avgSalary(company.getAvgSalary())
                .newHireAvgSalary(company.getNewHireAvgSalary())
                .employeeCount(company.getEmployeeCount())
                .ceoName(company.getCeoName())
                .companyHistory(company.getCompanyHistory())
                .build();
    }
}
