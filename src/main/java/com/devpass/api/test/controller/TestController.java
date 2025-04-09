package com.devpass.api.test.controller;

import com.devpass.api.test.converter.TestConverter;
import com.devpass.api.test.domain.Test;
import com.devpass.api.test.dto.request.TestRequest;
import com.devpass.api.test.dto.response.TestResponse;
import com.devpass.api.test.service.TestService;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
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
    public ApiResponse<TestResponse> createTest(@RequestBody TestRequest request) {
        Test test = testService.createTest(request);
        TestResponse response = TestConverter.toTestResponse(test);
        return ApiResponse.of(SuccessCode.OK, response);
    }
}
