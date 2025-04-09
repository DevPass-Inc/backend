package com.devpass.domain.devexperience.converter;

import com.devpass.domain.devexperience.dto.request.DevExperienceAddRequestDTO;
import com.devpass.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.domain.devexperience.entity.DevExperience;

public class DevExperienceConverter {

    public static DevExperience toEntity(DevExperienceAddRequestDTO request) {
        return DevExperience.builder()
                .user(null)
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }

    public static DevExperienceResponseDTO toResponse(DevExperience devExperience){
        return new DevExperienceResponseDTO(
                devExperience.getId(),
                devExperience.getTitle(),
                devExperience.getDescription()
        );
    }
}
