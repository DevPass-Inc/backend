package com.devpass.backend.domain.devexperience.controller;

import com.devpass.backend.domain.devexperience.dto.request.DevExperienceAddRequest;
import com.devpass.backend.domain.devexperience.dto.response.DevExperienceAggregateResponseDTO;
import com.devpass.backend.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.backend.domain.devexperience.service.DevExperienceService;
import com.devpass.backend.domain.devexperience.service.DevExperienceAggregateService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/dev-experiences")
@Tag(name = "개발경험 API", description = "새로운 개발경험 추가 및 조회")
public class DevExperienceController {

    private final DevExperienceService devExperienceService;
    private final DevExperienceAggregateService aggregateService;

    @Operation(
            summary = "devExprience(개발경험) 등록",
            description = "개발 경험 등록 페이지에서 경험 추가할 때 사용"
    )
    @PostMapping
    public CustomResponse<DevExperienceResponseDTO> addDevExperience(
            @RequestBody DevExperienceAddRequest request
    ) {
        DevExperienceResponseDTO response = devExperienceService.addDevExperience(request);
        return CustomResponse.of(ResultCode.CREATED, response);
    }
    @Operation(
            summary = "devExprience(개발경험) 리스트 조회",
            description = "개발 경험 리스트 조회"
    )
    @GetMapping
    public CustomResponse<List<DevExperienceResponseDTO>> getAllDevExperiences() {
        List<DevExperienceResponseDTO> responses = devExperienceService.getAllDevExperiences();
        return CustomResponse.of(ResultCode.OK, responses);
    }

    @Operation(
            summary = "devExprience(개발경험) 상세 조회 ",
            description = "개발 경험(프로젝트, 기술스택, 인턴십 경험)을 한 번에 조회"
    )
    @GetMapping("/{devExperienceId}")
    public CustomResponse<DevExperienceAggregateResponseDTO> getAggregateByDevExperienceId(
            @PathVariable("devExperienceId") Long devExperienceId) {
        DevExperienceAggregateResponseDTO response = aggregateService.getAggregateByDevExperienceId(devExperienceId);
        return CustomResponse.of(ResultCode.OK, response);
    }
}
