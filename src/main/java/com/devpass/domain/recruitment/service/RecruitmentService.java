package com.devpass.domain.recruitment.service;

import com.devpass.domain.recruitment.converter.RecruitmentConverter;
import com.devpass.domain.recruitment.dto.response.RecruitmentCardResponseDTO;
import com.devpass.domain.recruitment.dto.response.RecruitmentDetailResponseDTO;
import com.devpass.domain.recruitment.entity.Recruitment;
import com.devpass.domain.recruitment.exception.RecruitmentNotFoundException;
import com.devpass.domain.recruitment.repository.RecruitmentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 채용 공고 리스트 조회
    @Transactional(readOnly = true)
    public Page<RecruitmentCardResponseDTO> getRecruitmentCards(Pageable pageable) {
        Page<Recruitment> recruitments = recruitmentRepository.findAll(pageable);
        return recruitments.map(RecruitmentConverter::toRecruitmentCardResponse);
    }
}
