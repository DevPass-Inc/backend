package com.devpass.domain.resume.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.domain.resume.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumePersistenceService {

	private final ResumeRepository resumeRepository;

	public ResumeDocument saveResume(ResumeResponseDTO resumeResponseDTO, Long userId) {
		ResumeDocument document = new ResumeDocument();
		document.setUserId(userId);
		document.setResume(resumeResponseDTO);
		return resumeRepository.save(document);
	}

	@Transactional(readOnly = true)
	public Optional<ResumeDocument> findById(String resumeId) {
		return resumeRepository.findById(resumeId);
	}

	@Transactional(readOnly = true)
	public List<ResumeDocument> findByUserId(Long userId) {
		return resumeRepository.findAllByUserId(userId);
	}
}
