package com.devpass.backend.domain.recruitment.service;

import com.devpass.backend.domain.recruitment.converter.RecruitmentConverter;
import com.devpass.backend.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.backend.domain.recruitment.entity.Recruitment;
import com.devpass.backend.domain.recruitment.exception.RecruitmentNotFoundException;
import com.devpass.backend.domain.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;

    // 채용공고 개별 조회
    @Transactional(readOnly = true)
    public RecruitmentDetailResponseDTO getRecruitmentById(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow(RecruitmentNotFoundException::new);

        return RecruitmentConverter.toRecruitmentDetailResponse(recruitment);
    }
}
