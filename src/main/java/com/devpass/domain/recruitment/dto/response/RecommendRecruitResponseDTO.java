package com.devpass.domain.recruitment.dto.response;

import com.devpass.domain.stack.dto.response.StackStatusResponseDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RecommendRecruitResponseDTO {

    private String companyName;
    private String position;
    private String finalScore;
    private List<StackStatusResponseDTO> stacks;
}
