package com.devpass.domain.company.controller;

import com.devpass.domain.company.dto.response.CompanyDetailResponseDTO;
import com.devpass.domain.company.service.CompanyService;
import com.devpass.global.common.response.CustomResponse;
import com.devpass.global.result.ResultCode;
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
    @GetMapping("/{companyId}")
    public CustomResponse<CompanyDetailResponseDTO> getCompanyById(@PathVariable Long companyId) {
        CompanyDetailResponseDTO responseDto = companyService.getCompanyById(companyId);

        return CustomResponse.of(ResultCode.OK, responseDto);
    }
}
