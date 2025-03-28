package com.devpass.backend.domain.project.entity;

import com.devpass.backend.domain.devexperience.entity.DevExperience;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import com.devpass.backend.global.common.BaseEntity;

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
@Table(name = "projects")
public class Project extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "introduce", nullable = false, columnDefinition = "TEXT")
	private String introduce;

	@Column(name = "position")
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
	public Project(DevExperience devExperience, String title, String introduce, String position, LocalDate startDate, LocalDate endDate,
				   String content) {
		this.devExperience = devExperience;
		this.title = title;
		this.introduce = introduce;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
	}
}