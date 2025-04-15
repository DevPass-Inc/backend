package com.devpass.domain.internship.converter;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.internship.dto.request.InternshipAddRequestDTO;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.entity.Internship;

public class InternshipConverter {

    public static Internship toEntity(InternshipAddRequestDTO request, DevExperience devExperience) {
        return Internship.builder()
                .devExperience(devExperience) // 연관관계 설정
                .companyName(request.getCompanyName())
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .content(request.getContent())
                .build();
    }

    public static InternshipResponseDTO toResponse(Internship internship) {
        return new InternshipResponseDTO(
                internship.getId(),
                internship.getCompanyName(),
                internship.getPosition(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getContent()
        );
    }
}