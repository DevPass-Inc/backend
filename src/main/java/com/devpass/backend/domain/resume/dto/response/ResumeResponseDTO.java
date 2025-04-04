package com.devpass.backend.domain.resume.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.devpass.backend.domain.resume.dto.DescriptionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDTO {
    private String name;
    private String title;
    private String phone;
    private String email;
    private String github;
    private String blog;
    private List<String> summary;
    private List<Experience> experience;
    private List<Activity> activities;
    private List<Skill> skills;
    private Education education;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Experience {
        // 프로젝트명
        private String project;
        // 경험 요약
        private String summary;
        // 맡은 역할
        private String position;
        // 근무 기간
        private String duration;
        // 사용한 기술 스택 목록
        private List<String> skills;
        // 상세 설명 (단일 문자열 또는 문자열 배열 모두 지원)
        @JsonDeserialize(using = DescriptionDeserializer.class)
        private List<String> description;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Activity {
        // 활동 제목
        private String title;
        // 활동 기간
        private String duration;
        // 활동 상세 설명
        private String description;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Skill {
        // 기술 분류 (예: Frontend, Backend)
        private String position;
        // 해당 분류에 사용한 기술 목록
        private List<String> skills;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Education {
        // 학교명
        private String name;
        // 전공
        private String major;
        // 재학 기간 또는 졸업 연도
        private String duration;
    }
}
