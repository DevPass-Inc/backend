package com.devpass.api.test.converter;

import com.devpass.api.test.dto.request.TestRequest;
import com.devpass.api.test.domain.Test;
import com.devpass.api.test.dto.response.TestResponse;

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
