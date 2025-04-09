package com.devpass.domain.company.service;

import com.devpass.domain.company.converter.CompanyConverter;
import com.devpass.domain.company.dto.response.CompanyDetailResponseDTO;
import com.devpass.domain.company.entity.Company;
import com.devpass.domain.company.exception.CompanyNotFoundException;
import com.devpass.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    // 기업 개별 조회
    @Transactional(readOnly = true)
    public CompanyDetailResponseDTO getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(CompanyNotFoundException::new);

        return CompanyConverter.toCompanyDetailResponse(company);
    }
}
