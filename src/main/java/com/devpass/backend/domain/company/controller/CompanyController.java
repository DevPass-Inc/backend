package com.devpass.backend.domain.company.controller;

import com.devpass.backend.domain.company.converter.CompanyConverter;
import com.devpass.backend.domain.company.dto.response.CompanyDetailResponseDTO;
import com.devpass.backend.domain.company.entity.Company;
import com.devpass.backend.domain.company.service.CompanyService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Operation(summary = "기업 조회", description = "기업 개별 조회")
    @GetMapping("/{company_id}")
    public CustomResponse<CompanyDetailResponseDTO> getCompany(@PathVariable("company_id") Long companyId) {
        Company company = companyService.getCompany(companyId);

        CompanyDetailResponseDTO companyDetail = CompanyConverter.toCompanyDetailResponse(company);
        return CustomResponse.of(ResultCode.OK, companyDetail);
    }
}
