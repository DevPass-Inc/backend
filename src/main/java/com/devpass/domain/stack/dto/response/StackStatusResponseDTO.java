package com.devpass.domain.stack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StackStatusResponseDTO {
    private String name;
    private String isRequired;
}
