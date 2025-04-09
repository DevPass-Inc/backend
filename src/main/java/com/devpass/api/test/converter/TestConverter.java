package com.devpass.test.converter;

import com.devpass.test.domain.Test;
import com.devpass.test.dto.request.TestRequest;
import com.devpass.test.dto.response.TestResponse;

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
