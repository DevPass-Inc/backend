package com.devpass.backend.domain.project.entity;

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

	@Builder
	public Project(String title, String introduce, String position, LocalDate startDate, LocalDate endDate,
		String content) {
		this.title = title;
		this.introduce = introduce;
		this.position = position;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
	}
}