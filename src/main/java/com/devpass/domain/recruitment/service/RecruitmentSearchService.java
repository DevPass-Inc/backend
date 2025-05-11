package com.devpass.domain.recruitment.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.devpass.domain.recruitment.document.RecruitmentDocument;
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
public class RecruitmentSearchService {
    private final ElasticsearchClient elasticsearchClient;

    // 채용공고 검색하기
    public Page<RecruitmentDocument> searchByName(String keyword, String position, Pageable pageable) throws IOException {
        SearchResponse<RecruitmentDocument> response = elasticsearchClient.search(s -> s
                .index("recruitments")
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .query(q -> {
                    if ((keyword == null || keyword.isBlank()) && (position == null || position.isBlank())) {
                        return q.matchAll(m -> m);
                    } else {
                        return q.bool(b -> {
                            if (keyword != null && !keyword.isBlank()) {
                                b.must(m -> m
                                    .wildcard(w -> w
                                        .field("companyName.keyword")
                                        .value("*" + keyword + "*")
                                    )
                                );
                            }
                            if (position != null && !position.isBlank()) {
                                b.filter(f -> f.term(t -> t.field("position.keyword").value(position)));
                            }
                            return b;
                        });
                    }
                }),
            RecruitmentDocument.class
        );

        List<RecruitmentDocument> contents = response.hits().hits().stream()
            .map(Hit::source)
            .collect(Collectors.toList());

        return new PageImpl<>(
            contents,
            pageable,
            response.hits().total() != null ? response.hits().total().value() : contents.size()
        );
    }
}
