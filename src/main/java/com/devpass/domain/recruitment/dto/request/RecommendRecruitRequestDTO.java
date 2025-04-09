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
    @JsonProperty("user_stacks")
    private List<String> userStacks;

    @JsonProperty("user_resume")
    private String userResume;
}
