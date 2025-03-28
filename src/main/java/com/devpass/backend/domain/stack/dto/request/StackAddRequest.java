package com.devpass.backend.domain.stack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StackAddRequest {
    @Schema(description = "기술 스택", example = "springBoot, django, docker")
    private List<String> stacks;
}
