package com.devpass.backend.api.test.controller;

import com.devpass.backend.api.test.converter.TestConverter;
import com.devpass.backend.api.test.domain.Test;
import com.devpass.backend.api.test.dto.request.TestRequest;
import com.devpass.backend.api.test.dto.response.TestResponse;
import com.devpass.backend.api.test.service.TestService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {

    private final TestService testService;
    @Operation(summary = "테스트 생성", description = "1을 입력 시 성공 1이 아닌 숫자 입력 시 실패")
    @PostMapping("/test")
    public CustomResponse<TestResponse> createTest(@RequestBody TestRequest request) {
        Test test = testService.createTest(request);
        TestResponse response = TestConverter.toTestResponse(test);
        return CustomResponse.of(ResultCode.OK, response);
    }
}
