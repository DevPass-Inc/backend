package com.devpass.backend.domain.devexperience.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DevExperienceAddRequest {
    private String title;
    private String description;
}
