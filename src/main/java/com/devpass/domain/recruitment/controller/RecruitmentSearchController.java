package com.devpass.domain.recruitment.controller;

import com.devpass.domain.recruitment.document.RecruitmentDocument;
import com.devpass.domain.recruitment.service.RecruitmentSearchService;
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
@RequestMapping("/api/recruitments")
public class RecruitmentSearchController {
    private final RecruitmentSearchService recruitmentSearchService;

    @Operation(summary = "채용공고 검색", description = "채용공고 검색하기")
    @GetMapping("/")
    public ApiResponse<Page<RecruitmentDocument>> searchRecruitments(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String position,
        @PageableDefault(sort = "DESC") Pageable pageable)
        throws IOException {
        Page<RecruitmentDocument> recruitments = recruitmentSearchService.searchByName(keyword, position, pageable);
        return ApiResponse.of(SuccessCode.OK, recruitments);
    }
}
