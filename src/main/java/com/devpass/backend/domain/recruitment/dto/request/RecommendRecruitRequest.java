package com.devpass.backend.domain.recruitment.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendRecruitRequest {
    private List<String> userStacks;
    private String userResume;
}
