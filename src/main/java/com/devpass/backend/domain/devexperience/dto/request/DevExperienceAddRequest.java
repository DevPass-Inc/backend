package com.devpass.backend.domain.devexperience.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class DevExperienceAddRequest {
    private String title;
    private String description;
}
