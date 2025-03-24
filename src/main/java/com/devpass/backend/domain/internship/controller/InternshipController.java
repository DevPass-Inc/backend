package com.devpass.backend.domain.internship.controller;

import com.devpass.backend.domain.internship.dto.request.InternshipAddRequest;
import com.devpass.backend.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.backend.domain.internship.service.InternshipService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/internships")
public class InternshipController {

    private final InternshipService internshipService;

    // 인턴십 등록 API
    @PostMapping
    public CustomResponse<InternshipResponseDTO> addInternship(@RequestBody InternshipAddRequest request) {
        InternshipResponseDTO response = internshipService.addInternship(request);
        return CustomResponse.of(ResultCode.CREATED, response);
    }
}
