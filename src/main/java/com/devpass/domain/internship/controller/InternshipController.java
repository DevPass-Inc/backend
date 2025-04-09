package com.devpass.domain.internship.controller;

import com.devpass.domain.internship.dto.request.InternshipAddRequest;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.service.InternshipService;
import com.devpass.global.common.response.CustomResponse;
import com.devpass.global.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/internships")
@Tag(name = "Internship API", description = "인턴십 경험 관련 API")
public class InternshipController {

    private final InternshipService internshipService;
    @Operation(
            summary = "인턴십 경험 등록",
            description = "개발 경험 등록 페이지 - 인턴십 경험 등록(개발 경험 id를 기준으로 등록)"
    )
    @PostMapping("/{devExperience_id}")
    public CustomResponse<InternshipResponseDTO> addInternship(
            @PathVariable("devExperience_id") Long devExperienceId,
            @RequestBody InternshipAddRequest request) {
        internshipService.addInternship(devExperienceId, request);
        return CustomResponse.of(ResultCode.CREATED);
    }
}
