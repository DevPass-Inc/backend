package com.devpass.backend.domain.project.controller;

import com.devpass.backend.domain.project.dto.request.ProjectAddRequest;
import com.devpass.backend.domain.project.entity.Project;
import com.devpass.backend.domain.project.service.ProjectService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/projects")
public class ProjectController {

    private final ProjectService projectService;

    // 프로젝트 등록 API
    @PostMapping
    public CustomResponse<Project> addProject(@RequestBody ProjectAddRequest request) {
        Project project = projectService.addProject(request);
        return CustomResponse.of(ResultCode.CREATED, project);
    }

    // 프로젝트 조회 API
    @GetMapping("/{id}")
    public CustomResponse<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return CustomResponse.of(ResultCode.OK, project);
    }
}
