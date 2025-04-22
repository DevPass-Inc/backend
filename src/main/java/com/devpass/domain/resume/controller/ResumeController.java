package com.devpass.domain.resume.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.service.ResumeService;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

	private final ResumeService resumeService;

	@GetMapping("/devExprience/{devExperience_id}")
	public ApiResponse<ResumeDocument> generateResume(@AuthUser Long userId,
		@PathVariable("devExperience_id") Long devExperienceId) {
		ResumeDocument resume = resumeService.generateAndSaveResume(userId, devExperienceId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}

	@GetMapping("/{resume_id}")
	public ApiResponse<ResumeDocument> getResume(@AuthUser Long userId,
		@PathVariable("resume_id") String resumeId) {
		ResumeDocument resume = resumeService.getResumeById(userId, resumeId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}
}