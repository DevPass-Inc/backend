package com.devpass.backend.domain.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumePromptDTO {
    private String header;         // 프롬프트 헤더(지시문)
    private String jsonTemplate;   // 생성되어야 하는 JSON 템플릿(예시)
    private String aggregateData;  // 개발 경험 데이터(aggregate)를 JSON 문자열로 전달
    private String notes;          // 추가 지시사항
}
