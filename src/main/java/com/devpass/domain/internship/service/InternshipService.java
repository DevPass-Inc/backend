package com.devpass.domain.internship.service;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.internship.converter.InternshipConverter;
import com.devpass.domain.internship.dto.request.InternshipAddRequestDTO;
import com.devpass.domain.internship.dto.response.InternshipResponseDTO;
import com.devpass.domain.internship.entity.Internship;
import com.devpass.domain.internship.repository.InternshipRepository;
import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.GeneralException;
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
    public InternshipResponseDTO addInternship(Long devExperienceId, InternshipAddRequestDTO request) {
        DevExperience devExperience = devExperienceRepository.findById(devExperienceId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
        Internship internship = InternshipConverter.toEntity(request, devExperience);
        Internship saved = internshipRepository.save(internship);
        return InternshipConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public InternshipResponseDTO getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
        return InternshipConverter.toResponse(internship);
    }

    @Transactional(readOnly = true)
    public List<InternshipResponseDTO> getInternshipsByDevExperienceId(Long devExperienceId) {
        return internshipRepository.findAllByDevExperience_Id(devExperienceId)
                .stream()
                .map(InternshipConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteInternshipsByDevExperienceId(Long devExperienceId) {
        List<Internship> internships = internshipRepository.findAllByDevExperience_Id(devExperienceId);
        if (internships.isEmpty()) {
            throw new GeneralException(ErrorCode.NOT_FOUND);
        }
        internshipRepository.deleteAll(internships);
    }

    @Transactional
    public InternshipResponseDTO updateInternship(Long internshipId, InternshipAddRequestDTO request) {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
        internship.update(request);
        return InternshipConverter.toResponse(internship);
    }

}
