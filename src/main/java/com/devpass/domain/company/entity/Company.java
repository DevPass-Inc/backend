package com.devpass.domain.company.entity;

import com.devpass.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "companies")
public class Company extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "category", nullable = false)
	private String category;

	@Column(name = "location", nullable = false)
	private String location;

	@Column(name = "avg_salary")
	private String avgSalary;

	@Column(name = "new_hire_avg_salary")
	private String newHireAvgSalary;

	@Column(name = "employee_count")
	private Integer employeeCount;

	@Column(name = "ceo_name")
	private String ceoName;

	@Column(name = "company_history", columnDefinition = "TEXT")
	private String companyHistory;

	@Builder
	public Company(String name, String category, String location, String avgSalary, String newHireAvgSalary,
		Integer employeeCount, String ceoName, String companyHistory) {
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