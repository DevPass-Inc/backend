package com.devpass.backend.domain.internship.converter;

import com.devpass.backend.domain.internship.dto.request.InternshipAddRequest;
import com.devpass.backend.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.backend.domain.internship.entity.Internship;

public class InternshipConverter {

    public static Internship toEntity(InternshipAddRequest request) {
        return Internship.builder()
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