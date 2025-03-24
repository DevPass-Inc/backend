package com.devpass.backend.domain.devexperience.controller;

import com.devpass.backend.domain.devexperience.dto.request.DevExperienceAddRequest;
import com.devpass.backend.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.backend.domain.devexperience.service.DevExperienceService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/dev-experiences")
public class DevExperienceController {

    private final DevExperienceService devExperienceService;

    @PostMapping
    public CustomResponse<DevExperienceResponseDTO> addDevExperience(
            @RequestBody DevExperienceAddRequest request
    ) {
        DevExperienceResponseDTO response = devExperienceService.addDevExperience(request);
        return CustomResponse.of(ResultCode.CREATED, response);
    }

    @GetMapping("/{id}")
    public CustomResponse<DevExperienceResponseDTO> getDevExperience(@PathVariable Long id) {
        DevExperienceResponseDTO response = devExperienceService.getDevExperienceById(id);
        return CustomResponse.of(ResultCode.OK, response);
    }

    @GetMapping
    public CustomResponse<List<DevExperienceResponseDTO>> getAllDevExperiences() {
        List<DevExperienceResponseDTO> responses = devExperienceService.getAllDevExperiences();
        return CustomResponse.of(ResultCode.OK, responses);
    }

}
