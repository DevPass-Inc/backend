package com.devpass.domain.recruitment.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.devpass.domain.recruitment.document.RecruitmentDocument;
import java.io.IOException;
import java.util.ArrayList;
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
    public Page<RecruitmentDocument> searchByName(
        String keyword,
        String position,
        Integer minCareer,
        List<Long> stackIds,
        String region,
        String district,
        Pageable pageable
    ) throws IOException {

        boolean hasNoFilters =
            (keyword == null || keyword.isBlank()) &&
                (position == null || position.isBlank()) &&
                minCareer == null &&
                (stackIds == null || stackIds.isEmpty()) &&
                (region == null || region.isBlank()) &&
                (district == null || district.isBlank());

        SearchResponse<RecruitmentDocument> response = elasticsearchClient.search(s -> s
                .index("recruitments")
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .query(q -> {
                    if (hasNoFilters) {
                        return q.matchAll(m -> m);
                    }

                    return q.bool(b -> {
                        if (keyword != null && !keyword.isBlank()) {
                            b.must(m -> m
                                .wildcard(w -> w
                                    .field("companyName.keyword")
                                    .value("*" + keyword + "*")
                                )
                            );
                        }

                        List<Query> filters = new ArrayList<>();

                        if (position != null && !position.isBlank()) {
                            filters.add(Query.of(f -> f.term(t -> t.field("position.keyword").value(position))));
                        }

                        if (minCareer != null) {
                            filters.add(Query.of(f -> f.range(r -> r.field("maxCareer").gte(JsonData.of(minCareer)))));
                        }

                        if (stackIds != null && !stackIds.isEmpty()) {
                            filters.add(Query.of(f -> f.terms(t -> t
                                .field("stacks")
                                .terms(ts -> ts.value(stackIds.stream().map(FieldValue::of).toList()))
                            )));
                            filters.add(Query.of(f -> f.exists(e -> e.field("stacks"))));
                        }

                        if (region != null && !region.isBlank()) {
                            filters.add(Query.of(f -> f.term(t -> t.field("location.region").value(region))));
                        }

                        if (district != null && !district.isBlank()) {
                            filters.add(Query.of(f -> f.term(t -> t.field("location.district").value(district))));
                        }

                        b.filter(filters);
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
