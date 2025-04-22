package com.devpass.domain.resume.service;

import org.springframework.stereotype.Service;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.domain.resume.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumePersistenceService {

	private final ResumeRepository resumeRepository;

	public ResumeDocument saveResume(Long userId, ResumeResponseDTO resumeResponseDTO) {
		ResumeDocument document = new ResumeDocument();
		document.setResume(resumeResponseDTO);
		document.setUserId(userId);

		return resumeRepository.save(document);
	}
}
