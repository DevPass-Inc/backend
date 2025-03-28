package com.devpass.backend.domain.stack.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class StackAddRequest {
    private List<String> stacks;
}
