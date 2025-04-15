package com.devpass.backend.domain.resume.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.devpass.backend.domain.resume.dto.DescriptionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Experience {
        private String project;
        private String summary;
        private String position;
        private String duration;
        // skills 필드를 String으로 사용 (빈 문자열로 처리할 예정)
        private String skills;
        @JsonDeserialize(using = DescriptionDeserializer.class)
        private List<String> description;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Activity {
        // 여기서는 키 이름을 GPT 응답과 일치시키기 위해 activity, dates를 사용합니다.
        private String activity;
        private String dates;
        private String description;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Skill {
        private String skill;
        private String level;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Education {
        private String name;
        private String major;
        private String duration;
    }
}
