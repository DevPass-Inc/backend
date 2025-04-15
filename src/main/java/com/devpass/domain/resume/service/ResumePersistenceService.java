package com.devpass.domain.resume.service;


import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.domain.resume.repository.ResumeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumePersistenceService {

    private final ResumeRepository resumeRepository;

    public ResumeDocument saveResume(ResumeResponseDTO resumeResponseDTO) {
        ResumeDocument document = new ResumeDocument();
        document.setResume(resumeResponseDTO);
        return resumeRepository.save(document);
    }

    @Transactional(readOnly = true)
    public Optional<ResumeDocument> findById(String resumeId) {
        return resumeRepository.findById(resumeId);
    }

}
