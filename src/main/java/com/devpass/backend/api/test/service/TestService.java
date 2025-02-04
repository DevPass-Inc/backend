package com.devpass.backend.api.test.service;

import com.devpass.backend.api.test.converter.TestConverter;
import com.devpass.backend.api.test.domain.Test;
import com.devpass.backend.api.test.dto.request.TestRequest;
import com.devpass.backend.api.test.dto.response.TestResponse;
import com.devpass.backend.api.test.repository.TestRepository;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public Test createTest(TestRequest request) {

        if (request.getValue() != 1) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }

        Test test = TestConverter.toTestEntity(request);
        testRepository.save(test);

        return test;
    }
}