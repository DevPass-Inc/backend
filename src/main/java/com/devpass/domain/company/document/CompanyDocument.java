package com.devpass.domain.company.document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@Document(indexName = "companies")
public class CompanyDocument {
    @Id
    private Long id;
    private String name;
    private String category;
    private String location;
    private String avgSalary;
    private String newHireAvgSalary;
    private Integer employeeCount;
    private String ceoName;
    private String companyHistory;

    @Builder
    public CompanyDocument(Long id, String name, String category, String location, String avgSalary,
        String newHireAvgSalary, Integer employeeCount, String ceoName, String companyHistory) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.avgSalary = avgSalary;
        this.newHireAvgSalary = newHireAvgSalary;
        this.employeeCount = employeeCount;
        this.ceoName = ceoName;
        this.companyHistory = companyHistory;
    }
}
