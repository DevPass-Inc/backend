package com.devpass.domain.recruitment.entity;

import java.util.ArrayList;
import java.util.List;

import com.devpass.domain.stack.entity.Stack;
import com.devpass.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recruitments")
public class Recruitment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recruitment_id")
	private Long id;

	@Column(name = "company_name", nullable = false, columnDefinition = "TEXT")
	private String companyName;

	@Column(name = "position_name")
	private String positionName;

	@Column(name = "position", nullable = false)
	private String position;

	@Column(name = "location", nullable = false)
	private String location;

	@Column(name = "career")
	private String career;

	@Column(name = "main_task", columnDefinition = "TEXT")
	private String mainTask;

	@Column(name = "qualification", columnDefinition = "TEXT")
	private String qualification;

	@Column(name = "preferred", columnDefinition = "TEXT")
	private String preferred;

	@Column(name = "benefit", columnDefinition = "TEXT")
	private String benefit;

	@Column(name = "deadline")
	private String deadline;

	@Column(name = "image_url")
	private String imageUrl;

	@ManyToMany
	@JoinTable(
		name = "recruitment_stacks",
		joinColumns = @JoinColumn(name = "recruitment_id"),
		inverseJoinColumns = @JoinColumn(name = "stack_id")
	)
	private List<Stack> stacks = new ArrayList<>();

	@Builder
	public Recruitment(String companyName, String positionName, String position, String location, String career, String mainTask,
		String qualification, String preferred, String benefit, String deadline, String imageUrl) {
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