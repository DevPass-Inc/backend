package com.devpass.backend.domain.stack.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StackAddRequest {
    private List<String> stacks;
}
