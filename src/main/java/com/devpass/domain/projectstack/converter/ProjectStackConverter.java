package com.devpass.domain.projectstack.converter;

import com.devpass.domain.project.entity.Project;
import com.devpass.domain.projectstack.entity.ProjectStack;
import com.devpass.domain.stack.entity.Stack;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectStackConverter {
    public static List<ProjectStack> toProjectStacks(List<Stack> stacks, Project project) {
        return stacks.stream()
            .map(stack -> ProjectStack.builder()
                .project(project)
                .stack(stack)
                .build())
            .collect(Collectors.toList());
    }
}
