package com.devpass.domain.stack.dto.response;

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
