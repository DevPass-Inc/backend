package com.devpass.backend.domain.resume.controller;

import com.devpass.backend.domain.resume.document.ResumeDocument;
import com.devpass.backend.domain.resume.service.ResumeService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    // 생성 및 저장 API: devExperience_id와 recruitmentStack_id를 함께 받아 이력서를 생성 및 저장
    @GetMapping("/generate/{devExperience_id}/{recruitmentStack_id}")
    public CustomResponse<ResumeDocument> generateResume(@PathVariable("devExperience_id") Long devExperienceId,
                                                         @PathVariable("recruitmentStack_id") Long recruitmentStackId) {
        ResumeDocument resume = resumeService.generateAndSaveResume(devExperienceId, recruitmentStackId);
        return CustomResponse.of(ResultCode.OK, resume);
    }

    // 조회 API: 저장된 이력서를 resume_id로 조회
    @GetMapping("/{resume_id}")
    public CustomResponse<ResumeDocument> getResume(@PathVariable("resume_id") String resumeId) {
        ResumeDocument resume = resumeService.getResumeById(resumeId);
        return CustomResponse.of(ResultCode.OK, resume);
    }
}
