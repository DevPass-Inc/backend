package com.devpass.domain.recruitment.document;

import jakarta.persistence.Id;
import java.util.List;
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

    // 기업 정보
    private Long companyId;
    private String companyName;
    private String employeeCount;
    private Integer newHireAvgSalary;

    // 채용공고 정보
    private String positionName;
    private String position;
    private Location location;
    private String career;
    private String mainTask;
    private String qualification;
    private String preferred;
    private String benefit;
    private String deadline;
    private String imageUrl;
    private Integer minCareer;
    private Integer maxCareer;
    private List<Long> stacks;

    @Builder
    public RecruitmentDocument(String id, Long companyId, String companyName,
        String employeeCount, Integer newHireAvgSalary,
        String positionName, String position, Location location, String career,
        String mainTask, String qualification, String preferred, String benefit,
        String deadline, String imageUrl,
        Integer minCareer, Integer maxCareer,
        List<Long> stacks) {

        this.id = id;
        this.companyId = companyId;
        this.companyName = companyName;
        this.employeeCount = employeeCount;
        this.newHireAvgSalary = newHireAvgSalary;

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
        this.minCareer = minCareer;
        this.maxCareer = maxCareer;
        this.stacks = stacks;
    }
}

