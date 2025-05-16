package com.devpass.domain.company.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.devpass.domain.company.document.CompanyDocument;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanySearchService {
    private final ElasticsearchClient elasticsearchClient;

    // 기업 검색하기
    public Page<CompanyDocument> searchByName(String keyword, Pageable pageable) throws IOException {
        SearchResponse<CompanyDocument> response = elasticsearchClient.search(s -> s
                .index("companies")
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .query(q -> q
                    .wildcard(w -> w
                        .field("name.keyword")
                        .value("*" + keyword + "*")
                    )
                ),
            CompanyDocument.class
        );

        List<CompanyDocument> contents = response.hits().hits().stream()
            .map(Hit::source)
            .collect(Collectors.toList());

        return new PageImpl<>(contents, pageable, response.hits().total() != null ? response.hits().total().value() : contents.size());
    }
}
