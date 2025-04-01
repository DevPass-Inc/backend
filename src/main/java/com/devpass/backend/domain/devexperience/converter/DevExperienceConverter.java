package com.devpass.backend.domain.devexperience.converter;

import com.devpass.backend.domain.devexperience.dto.request.DevExperienceAddRequest;
import com.devpass.backend.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.backend.domain.devexperience.entity.DevExperience;
import com.devpass.backend.domain.user.entity.User;

public class DevExperienceConverter {

    public static DevExperience toEntity(DevExperienceAddRequest request) {
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
