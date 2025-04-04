package com.devpass.backend.domain.resume.controller;

import com.devpass.backend.domain.resume.document.ResumeDocument;
import com.devpass.backend.domain.resume.dto.response.ResumeResponseDTO;
import com.devpass.backend.domain.resume.service.ResumeService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/devExprience/{devExperience_id}")
    public CustomResponse<ResumeDocument> generateResume(@PathVariable("devExperience_id") Long devExperienceId) {
        ResumeDocument resume = resumeService.generateAndSaveResume(devExperienceId);
        return CustomResponse.of(ResultCode.OK, resume);
    }


    @GetMapping("/{resume_id}")
    public CustomResponse<ResumeDocument> getResume(@PathVariable("resume_id") String resumeId) {
        ResumeDocument resume = resumeService.getResumeById(resumeId);
        return CustomResponse.of(ResultCode.OK, resume);
    }

}
