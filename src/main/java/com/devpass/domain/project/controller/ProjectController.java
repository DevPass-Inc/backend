package com.devpass.domain.project.controller;

import com.devpass.domain.project.dto.response.ProjectAddResponseDTO;
import com.devpass.domain.project.dto.response.ProjectsResponseDTO;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.project.dto.request.ProjectAddRequestDTO;
import com.devpass.domain.project.dto.response.ProjectResponseDTO;
import com.devpass.domain.project.service.ProjectService;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/projects")
@Tag(name = "프로젝트 API")
public class ProjectController {

	private final ProjectService projectService;

	@Operation(summary = "프로젝트 경험 등록")
	@PostMapping("/{devExperienceId}")
	public ApiResponse<ProjectAddResponseDTO> addProject(
		@AuthUser Long userId,
		@PathVariable("devExperienceId") Long devExperienceId,
		@RequestBody ProjectAddRequestDTO request) {
		ProjectAddResponseDTO projectResponseDTO = projectService.addProject(userId, devExperienceId, request);
		return ApiResponse.of(SuccessCode.CREATED, projectResponseDTO);
	}

	@Operation(summary = "프로젝트 경험 상세 조회")
	@GetMapping("/{projectId}")
	public ApiResponse<ProjectResponseDTO> getProject(@AuthUser Long userId, @PathVariable("projectId") Long id) {
		ProjectResponseDTO projectResponseDTO = projectService.getProjectById(userId, id);
		return ApiResponse.of(SuccessCode.OK, projectResponseDTO);
	}

	@Operation(summary = "프로젝트 경험 삭제")
	@DeleteMapping("/{projectId}")
	public ApiResponse<Void> deleteProjects(
		@PathVariable("projectId") Long projectId) {
		projectService.deleteByProjectId(projectId);
		return ApiResponse.of(SuccessCode.OK);
	}

	@Operation(summary = "프로젝트 경험 수정")
	@PutMapping("/{projectId}")
	public ApiResponse<ProjectResponseDTO> updateProject(
		@PathVariable("projectId") Long projectId,
		@RequestBody ProjectAddRequestDTO request) {
		ProjectResponseDTO updated = projectService.updateProject(projectId, request);
		return ApiResponse.of(SuccessCode.OK, updated);
	}

	@Operation(summary = "개발 경험에 속한 프로젝트 리스트 조회")
	@GetMapping("/dev-experience/{devExperienceId}")
	public ApiResponse<ProjectsResponseDTO> getProjectsByDevExperienceId(
		@AuthUser Long userId,
		@PathVariable("devExperienceId") Long devExperienceId) {
		List<ProjectAddResponseDTO> projects = projectService.getProjectsByDevExperienceId(userId, devExperienceId);
		ProjectsResponseDTO response = new ProjectsResponseDTO(projects);
		return ApiResponse.of(SuccessCode.OK, response);
	}
}
