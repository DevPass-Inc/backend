package com.devpass.backend.domain.devexperience.service;

import com.devpass.backend.domain.devexperience.converter.DevExperienceConverter;
import com.devpass.backend.domain.devexperience.dto.request.DevExperienceAddRequest;
import com.devpass.backend.domain.devexperience.dto.response.DevExperienceResponseDTO;
import com.devpass.backend.domain.devexperience.entity.DevExperience;
import com.devpass.backend.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.backend.domain.user.entity.User;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DevExperienceService {

    private final DevExperienceRepository devExperienceRepository;

    @Transactional
    public DevExperienceResponseDTO addDevExperience(DevExperienceAddRequest request) {
        DevExperience devExperience = DevExperienceConverter.toEntity(request);
        DevExperience saved = devExperienceRepository.save(devExperience);
        return DevExperienceConverter.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<DevExperienceResponseDTO> getAllDevExperiences() {
        return devExperienceRepository.findAll().stream()
                .map(DevExperienceConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DevExperienceResponseDTO getDevExperienceById(Long id) {
        DevExperience devExperience = devExperienceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        return DevExperienceConverter.toResponse(devExperience);
    }

}
