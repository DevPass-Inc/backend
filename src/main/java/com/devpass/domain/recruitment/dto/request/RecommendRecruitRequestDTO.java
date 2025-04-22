package com.devpass.domain.recruitment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRecruitRequestDTO {
    @JsonProperty("resume_id")
    private String resumeId;
}
