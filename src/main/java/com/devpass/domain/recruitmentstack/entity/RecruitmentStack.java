package com.devpass.domain.recruitmentstack.entity;

import com.devpass.domain.recruitment.entity.Recruitment;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.global.common.BaseEntity;

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
@Table(name = "recruitment_stacks")
public class RecruitmentStack extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recruitment_id", nullable = false)
	private Recruitment recruitment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stack_id", nullable = false)
	private Stack stack;

	@Builder
	public RecruitmentStack(Recruitment recruitment, Stack stack) {
		this.recruitment = recruitment;
		this.stack = stack;
	}
}