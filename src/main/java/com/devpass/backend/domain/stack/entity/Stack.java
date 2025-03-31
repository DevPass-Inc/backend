package com.devpass.backend.domain.stack.entity;

import com.devpass.backend.domain.devexperience.entity.DevExperience;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

import com.devpass.backend.domain.recruitment.entity.Recruitment;
import com.devpass.backend.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stacks")
public class Stack extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stack_id")
	private Long id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "stacks")
	private List<Recruitment> recruitments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dev_experience_id", nullable = false)
	private DevExperience devExperience;

	@Builder
	public Stack(String name, DevExperience devExperience) {
		this.name = name;
		this.devExperience = devExperience;

	}
}