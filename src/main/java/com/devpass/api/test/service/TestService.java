package com.devpass.test.service;

import com.devpass.test.converter.TestConverter;
import com.devpass.test.domain.Test;
import com.devpass.test.dto.request.TestRequest;
import com.devpass.test.repository.TestRepository;
import com.devpass.global.error.ErrorCode;
import com.devpass.global.error.exception.BusinessException;
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