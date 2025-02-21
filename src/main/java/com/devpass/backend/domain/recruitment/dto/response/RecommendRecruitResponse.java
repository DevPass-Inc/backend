package com.devpass.backend.domain.recruitment.dto.response;

import com.devpass.backend.domain.stack.dto.response.StackStatusResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RecommendRecruitResponse {

    private String companyName;
    private String position;
    private String finalScore;
    private List<StackStatusResponse> stacks;
}
