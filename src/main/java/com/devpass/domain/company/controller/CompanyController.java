package com.devpass.domain.company.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.company.dto.response.CompanyDetailResponseDTO;
import com.devpass.domain.company.service.CompanyService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
@Tag(name = "기업 API")
public class CompanyController {

	private final CompanyService companyService;

	@Operation(summary = "기업 상세 조회")
	@GetMapping("/{companyId}")
	public ApiResponse<CompanyDetailResponseDTO> getCompanyById(
		@PathVariable Long companyId) {
		CompanyDetailResponseDTO responseDto = companyService.getCompanyById(companyId);
		return ApiResponse.of(SuccessCode.OK, responseDto);
	}
}
