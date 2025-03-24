package com.devpass.backend.domain.internship.service;

import com.devpass.backend.domain.internship.converter.InternshipConverter;
import com.devpass.backend.domain.internship.dto.request.InternshipAddRequest;
import com.devpass.backend.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.backend.domain.internship.entity.Internship;
import com.devpass.backend.domain.internship.repository.InternshipRepository;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;

    @Transactional
    public InternshipResponseDTO addInternship(InternshipAddRequest request) {
        Internship internship = InternshipConverter.toEntity(request);
        Internship saved = internshipRepository.save(internship);
        return InternshipConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public InternshipResponseDTO getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        return InternshipConverter.toResponse(internship);
    }
}
