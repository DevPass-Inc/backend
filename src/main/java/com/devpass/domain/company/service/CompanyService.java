package com.devpass.domain.company.service;

import com.devpass.domain.company.converter.CompanyConverter;
import com.devpass.domain.company.document.CompanyDocument;
import com.devpass.domain.company.dto.response.CompanyDetailResponseDTO;
import com.devpass.domain.company.entity.Company;
import com.devpass.domain.company.exception.CompanyNotFoundException;
import com.devpass.domain.company.repository.CompanyRepository;
import com.devpass.domain.company.repository.CompanySearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanySearchRepository companySearchRepository;

    // 기업 개별 조회
    @Transactional(readOnly = true)
    public CompanyDetailResponseDTO getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(CompanyNotFoundException::new);

        return CompanyConverter.toCompanyDetailResponse(company);
    }

    // 기업 검색하기
    public Page<CompanyDocument> searchCompanies(String keyword, Pageable pageable) {
        return companySearchRepository.searchByName(keyword, pageable);
    }
}
