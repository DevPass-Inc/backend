package com.devpass.domain.resume.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.resume.document.ResumeDocument;
import com.devpass.domain.resume.service.ResumeService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

	private final ResumeService resumeService;

	// 생성 및 저장 API: devExperience_id와 recruitmentStack_id를 함께 받아 이력서를 생성 및 저장
	@GetMapping("/generate/{devExperience_id}/{recruitmentStack_id}")
	public ApiResponse<ResumeDocument> generateResume(
		@PathVariable("devExperience_id") Long devExperienceId,
		@PathVariable("recruitmentStack_id") Long recruitmentStackId) {
		ResumeDocument resume = resumeService.generateAndSaveResume(devExperienceId, recruitmentStackId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}

	// 조회 API: 저장된 이력서를 resume_id로 조회
	@GetMapping("/{resume_id}")
	public ApiResponse<ResumeDocument> getResume(
		@PathVariable("resume_id") String resumeId) {
		ResumeDocument resume = resumeService.getResumeById(resumeId);
		return ApiResponse.of(SuccessCode.OK, resume);
	}
}
