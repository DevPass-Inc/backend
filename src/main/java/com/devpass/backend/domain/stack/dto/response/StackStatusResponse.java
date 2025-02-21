package com.devpass.backend.domain.stack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StackStatusResponse {
    private String stack;
    private boolean isRequired;
}
