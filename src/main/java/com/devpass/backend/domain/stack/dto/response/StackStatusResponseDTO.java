package com.devpass.backend.domain.stack.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StackStatusResponseDTO {

    @Schema(description = "기술 스택", example = "springBoot, django, docker")
    private String stack;
    private boolean isRequired;
}
