package com.devpass.domain.company.repository;

import com.devpass.domain.company.document.CompanyDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySearchRepository extends ElasticsearchRepository<CompanyDocument, Long> {
    Page<CompanyDocument> searchByName(String keyword, Pageable pageable);
}
