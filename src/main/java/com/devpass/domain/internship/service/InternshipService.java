package com.devpass.domain.internship.service;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.internship.converter.InternshipConverter;
import com.devpass.domain.internship.dto.request.InternshipAddRequest;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.entity.Internship;
import com.devpass.domain.internship.repository.InternshipRepository;
import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final DevExperienceRepository devExperienceRepository;

    @Transactional
    public InternshipResponseDTO addInternship(Long devExperienceId, InternshipAddRequest request) {
        DevExperience devExperience = devExperienceRepository.findById(devExperienceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        Internship internship = InternshipConverter.toEntity(request, devExperience);
        Internship saved = internshipRepository.save(internship);
        return InternshipConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public InternshipResponseDTO getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        return InternshipConverter.toResponse(internship);
    }

    @Transactional(readOnly = true)
    public List<InternshipResponseDTO> getInternshipsByDevExperienceId(Long devExperienceId) {
        return internshipRepository.findAllByDevExperience_Id(devExperienceId)
                .stream()
                .map(InternshipConverter::toResponse)
                .collect(Collectors.toList());
    }
}
