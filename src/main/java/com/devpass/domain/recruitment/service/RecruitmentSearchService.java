package com.devpass.domain.recruitment.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
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
    public Page<RecruitmentDocument> searchByName(String keyword, String position, Integer minCareer, Pageable pageable) throws IOException {
        SearchResponse<RecruitmentDocument> response = elasticsearchClient.search(s -> s
                .index("recruitments")
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .query(q -> {
                    if ((keyword == null || keyword.isBlank()) &&
                        (position == null || position.isBlank()) &&
                        minCareer == null) {
                        return q.matchAll(m -> m);
                    }

                    return q.bool(b -> {
                        // 키워드 검색
                        if (keyword != null && !keyword.isBlank()) {
                            b.must(m -> m
                                .wildcard(w -> w
                                    .field("companyName.keyword")
                                    .value("*" + keyword + "*")
                                )
                            );
                        }

                        // 포지션 필터링
                        if (position != null && !position.isBlank()) {
                            b.filter(f -> f.term(t -> t.field("position.keyword").value(position)));
                        }

                        // 경럭 필터링
                        if (minCareer != null) {
                            b.filter(f -> f.range(r -> r.field("maxCareer").gte(JsonData.of(minCareer))));
                        }

                        return b;
                    });
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
