package com.devpass.domain.recruitment.converter;

import com.devpass.domain.recruitment.dto.response.RecruitmentCardResponseDTO;
import com.devpass.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.domain.recruitment.entity.Recruitment;
import com.devpass.domain.stack.entity.Stack;
import java.util.List;
import java.util.stream.Collectors;

public class RecruitmentConverter {

    public static RecruitmentDetailResponseDTO toRecruitmentDetailResponse(Recruitment recruitment) {
        return RecruitmentDetailResponseDTO.builder()
            .recruitmentId(recruitment.getId())
            .companyName(recruitment.getCompanyName())
            .position(recruitment.getPosition())
            .location(recruitment.getLocation())
            .career(recruitment.getCareer())
            .mainTask(recruitment.getMainTask())
            .qualification(recruitment.getQualification())
            .preferred(recruitment.getPreferred())
            .benefit(recruitment.getBenefit())
            .deadline(recruitment.getDeadline())
            .imageUrl(recruitment.getImageUrl())
            .build();
    }

    public static RecruitmentCardResponseDTO toRecruitmentCardResponse(Recruitment recruitment) {
        List<String> stackNames = recruitment.getStacks().stream()
            .map(Stack::getName)
            .collect(Collectors.toList());

        return RecruitmentCardResponseDTO.builder()
            .id(recruitment.getId())
            .imageUrl(recruitment.getImageUrl())
            .companyName(recruitment.getCompanyName())
            .position(recruitment.getPosition())
            .career(recruitment.getCareer())
            .location(recruitment.getLocation())
            .stacks(stackNames)
            .build();
    }
}
