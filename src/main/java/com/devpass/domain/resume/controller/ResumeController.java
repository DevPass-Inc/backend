package com.devpass.domain.resume.controller;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.service.ResumeService;
import com.devpass.global.payload.wrapper.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
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
    public ApiResponse<ResumeDocument> generateResume(@PathVariable("devExperience_id") Long devExperienceId) {
        ResumeDocument resume = resumeService.generateAndSaveResume(devExperienceId);
        return ApiResponse.of(SuccessCode.OK, resume);
    }


    @GetMapping("/{resume_id}")
    public ApiResponse<ResumeDocument> getResume(@PathVariable("resume_id") String resumeId) {
        ResumeDocument resume = resumeService.getResumeById(resumeId);
        return ApiResponse.of(SuccessCode.OK, resume);
    }

}
