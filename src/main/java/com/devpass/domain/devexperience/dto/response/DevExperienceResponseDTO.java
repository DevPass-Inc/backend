package com.devpass.domain.devexperience.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DevExperienceResponseDTO {
    private Long id;
    private String title;
    private String description;
}
