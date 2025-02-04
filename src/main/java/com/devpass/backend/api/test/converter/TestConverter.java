package com.devpass.backend.api.test.converter;

import com.devpass.backend.api.test.domain.Test;
import com.devpass.backend.api.test.dto.request.TestRequest;
import com.devpass.backend.api.test.dto.response.TestResponse;

public class TestConverter {

    public static Test toTestEntity(TestRequest request) {
        return Test.builder()
            .value(request.getValue())
            .build();
    }

    public static TestResponse toTestResponse(Test test) {
        return TestResponse.builder()
            .id(test.getId())
            .value(test.getValue())
            .build();
    }
}
