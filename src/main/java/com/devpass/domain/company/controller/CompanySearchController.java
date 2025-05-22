package com.devpass.domain.company.controller;

import com.devpass.domain.company.document.CompanyDocument;
import com.devpass.domain.company.service.CompanySearchService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanySearchController {
    private final CompanySearchService companySearchService;

    @Operation(summary = "기업 검색", description = "기업 검색하기")
    @GetMapping("/")
    public ApiResponse<Page<CompanyDocument>> searchCompanies(
        @RequestParam String keyword, @PageableDefault(sort = "DESC") Pageable pageable)
        throws IOException {
        Page<CompanyDocument> companies = companySearchService.searchByName(keyword, pageable);
        return ApiResponse.of(SuccessCode.OK, companies);
    }
}
