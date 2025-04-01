package com.devpass.backend.domain.resume.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.devpass.backend.domain.resume.dto.DescriptionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDTO {
    private List<String> summary;
    private List<Experience> experience;
    private List<Activity> activities;
    private List<Skill> skills;
    private Education education;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Experience {
        private String title;
        private String company;
        private String dates;
        @JsonDeserialize(using = DescriptionDeserializer.class)
        private List<String> description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Activity {
        private String activity;
        private String dates;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Skill {
        private String skill;
        private String level;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Education {
        private String degree;
        private String school;
        private String dates;
    }
}
