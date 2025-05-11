package com.devpass.domain.recruitment.document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@Document(indexName = "recruitments")
public class RecruitmentDocument {
    @Id
    private String id;
    private String companyName;
    private String positionName;
    private String position;
    private String location;
    private String career;
    private String mainTask;
    private String qualification;
    private String preferred;
    private String benefit;
    private String deadline;
    private String imageUrl;

    @Builder
    public RecruitmentDocument(String id, String companyName, String positionName, String position, String location, String career,
                               String mainTask, String qualification, String preferred, String benefit,
                               String deadline, String imageUrl) {
        this.id = id;
        this.companyName = companyName;
        this.positionName = positionName;
        this.position = position;
        this.location = location;
        this.career = career;
        this.mainTask = mainTask;
        this.qualification = qualification;
        this.preferred = preferred;
        this.benefit = benefit;
        this.deadline = deadline;
        this.imageUrl = imageUrl;
    }
}
