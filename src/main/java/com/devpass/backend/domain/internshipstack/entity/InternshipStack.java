package com.devpass.backend.domain.internshipstack.entity;

import com.devpass.backend.domain.internship.entity.Internship;
import com.devpass.backend.domain.stack.entity.Stack;
import com.devpass.backend.global.common.BaseEntity;

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
@Table(name = "internship_stacks")
public class InternshipStack extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internship_id", nullable = false)
	private Internship internship;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stack_id", nullable = false)
	private Stack stack;

	@Builder
	public InternshipStack(Internship internship, Stack stack) {
		this.internship = internship;
		this.stack = stack;
	}
}