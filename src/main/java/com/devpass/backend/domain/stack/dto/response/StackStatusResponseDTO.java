package com.devpass.backend.domain.stack.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StackStatusResponseDTO {
    private String stack;
    private boolean isRequired;

    public String getName() {
        return this.stack;
    }
}
