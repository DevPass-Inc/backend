package com.devpass.domain.stack.entity;

import com.devpass.domain.devexpstack.entity.DevExpStack;
import com.devpass.domain.projectstack.entity.ProjectStack;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;

import com.devpass.domain.recruitment.entity.Recruitment;
import com.devpass.global.common.entity.BaseEntity;

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

	@OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProjectStack> projectStacks;

	@OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DevExpStack> devExpStacks;

	@Builder
	public Stack(String name, List<ProjectStack> projectStacks, List<DevExpStack> devExpStacks) {
		this.name = name;
		this.projectStacks = projectStacks;
		this.devExpStacks = devExpStacks;

	}
}