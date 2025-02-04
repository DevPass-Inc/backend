package com.devpass.backend.api.test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TestResponse {
    private Long id;
    private int value;
}
