package com.devpass.backend.domain.recruitment.converter;

import com.devpass.backend.domain.recruitment.dto.response.RecruitmentDetailResponse;
import com.devpass.backend.domain.recruitment.entity.Recruitment;

public class RecruitmentConverter {

    public static RecruitmentDetailResponse toRecruitmentDetailResponse(Recruitment recruitment) {
        return RecruitmentDetailResponse.builder()
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
}
