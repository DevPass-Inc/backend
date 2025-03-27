package com.devpass.backend.domain.company.service;

import com.devpass.backend.domain.company.entity.Company;
import com.devpass.backend.domain.company.exception.CompanyNotFoundException;
import com.devpass.backend.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    // 기업 개별 조회
    public Company getCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(CompanyNotFoundException::new);

        return company;
    }
}
