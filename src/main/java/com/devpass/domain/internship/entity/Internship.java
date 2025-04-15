package com.devpass.domain.internship.entity;

import com.devpass.domain.devexperience.entity.DevExperience;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import com.devpass.global.common.entity.BaseEntity;

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
@Table(name = "internships")
public class Internship extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Column(name = "position", nullable = false)
	private String position;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dev_experience_id", nullable = false)
	private DevExperience devExperience;

	@Builder
	public Internship(DevExperience devExperience, String companyName, String position, LocalDate startDate, LocalDate endDate, String content) {
		this.devExperience = devExperience;
		this.companyName = companyName;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
	}
}