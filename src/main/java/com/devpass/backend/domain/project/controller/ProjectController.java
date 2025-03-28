package com.devpass.backend.domain.project.controller;

import com.devpass.backend.domain.project.dto.request.ProjectAddRequest;
import com.devpass.backend.domain.project.entity.Project;
import com.devpass.backend.domain.project.service.ProjectService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/projects")
@Tag(name = "Project API", description = "프로젝트 경험 등록 관련 API")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(
            summary = "Project 경험 등록",
            description = "경험등록 페이지 - 프로젝트 경험 등록"
    )
    // 프로젝트 등록 API
    @PostMapping("/{devExperience_id}")
    public CustomResponse<Project> addProject(
            @PathVariable("devExperience_id") Long devExperienceId,
            @RequestBody ProjectAddRequest request) {
        Project project = projectService.addProject(devExperienceId, request);
        return CustomResponse.of(ResultCode.CREATED, project);
    }


    // 프로젝트 조회 API
    @GetMapping("/{project_id}")
    public CustomResponse<Project> getProject(@PathVariable("project_id") Long id) {
        Project project = projectService.getProjectById(id);
        return CustomResponse.of(ResultCode.OK, project);
    }
}
