package com.devpass.domain.recruitment.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentCardResponseDTO {
    private Long id;
    private String imageUrl;
    private String companyName;
    private String position;
    private String career;
    private String location;
    private List<String> stacks;
}