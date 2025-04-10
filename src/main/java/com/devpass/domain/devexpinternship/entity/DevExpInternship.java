package com.devpass.domain.devexpinternship.entity;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.internship.entity.Internship;
import com.devpass.global.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dev_exp_internships")
public class DevExpInternship extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dev_experience_id", nullable = false)
	private DevExperience devExperience;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "experience_id", nullable = false)
	private Internship internship;

	@Builder
	public DevExpInternship(DevExperience devExperience, Internship internship) {
		this.devExperience = devExperience;
		this.internship = internship;
	}
}