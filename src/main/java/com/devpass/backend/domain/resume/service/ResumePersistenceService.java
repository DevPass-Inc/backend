package com.devpass.backend.domain.resume.service;

import com.devpass.backend.domain.resume.document.ResumeDocument;
import com.devpass.backend.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.backend.domain.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumePersistenceService {

    private final ResumeRepository resumeRepository;

    public ResumeDocument saveResume(ResumeResponseDTO resumeResponseDTO) {
        ResumeDocument document = new ResumeDocument();
        document.setResume(resumeResponseDTO);
        return resumeRepository.save(document);
    }
}
