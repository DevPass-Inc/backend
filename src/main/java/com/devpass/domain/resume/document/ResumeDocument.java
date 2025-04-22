package com.devpass.domain.resume.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.devpass.domain.resume.dto.response.ResumeResponseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "resumes")
@Getter
@Setter
@NoArgsConstructor
public class ResumeDocument {
	@Id
	private String id;

	private Long userId;

	// 생성된 이력서를 객체로 저장
	private ResumeResponseDTO resume;

}
