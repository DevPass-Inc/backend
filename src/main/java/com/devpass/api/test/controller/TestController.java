package com.devpass.test.controller;

import com.devpass.test.converter.TestConverter;
import com.devpass.test.domain.Test;
import com.devpass.test.dto.request.TestRequest;
import com.devpass.test.dto.response.TestResponse;
import com.devpass.test.service.TestService;
import com.devpass.global.common.response.CustomResponse;
import com.devpass.global.result.ResultCode;
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
