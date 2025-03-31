package com.devpass.backend.domain.stack.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StackListResponseDTO {
    private List<StackStatusResponseDTO> stacks;
}
